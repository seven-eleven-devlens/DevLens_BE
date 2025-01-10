package com.seveneleven.devlens.global.util.file.Service;

import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.ErrorCode;
import com.seveneleven.devlens.global.response.SuccessCode;
import com.seveneleven.devlens.global.util.file.FileValidator;
import com.seveneleven.devlens.global.util.file.constant.FileCategory;
import com.seveneleven.devlens.global.util.file.dto.FileMetadataDto;
import com.seveneleven.devlens.global.util.file.entity.FileMetadata;
import com.seveneleven.devlens.global.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMetadataRepository fileMetadataRepository;
    private final S3ClientService s3ClientService;

    private static final double KILOBYTE_CONVERSION_CONSTANT = 1024.0;

    /**
     * 1. 파일 업로드
     * @param file 업로드할 파일
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return FileMetadataDto 업로드한 파일 메타데이터
     */
    @Transactional
    public APIResponse uploadFile(MultipartFile file, String fileCategory, Long referenceId) throws Exception {
        //1. 파일 검증
        FileValidator.validateFile(file, fileCategory);

        //2. 고유 파일 이름(UUID) 및 S3 키 생성
        //파일명이 없거나 비어있으면 Unknown-File로 설정
        String originalFilename = (file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
                ? file.getOriginalFilename() : "Unknown-File";
        //UUID 생성
        String uniqueFileName = s3ClientService.generateUniqueFileName(originalFilename);
        //S3 키 생성
        String s3Key = s3ClientService.generateS3Key(fileCategory, referenceId, uniqueFileName);

        //3. S3 업로드 및 FileMetadata 데이터 생성
        String filePath = null;
        try {
            //S3 업로드
            filePath = s3ClientService.uploadFile(file, s3Key);

            //업로드한 파일의 category Enum 가져오기
            FileCategory categoryEnum = FileCategory.valueOf(fileCategory);

            //entity 생성자 호출
            FileMetadata fileMetadata = FileMetadata.registerFile(categoryEnum, referenceId,
                    file.getOriginalFilename(), uniqueFileName, file.getContentType(),
                    file.getOriginalFilename().substring(originalFilename.lastIndexOf('.') + 1),
                    file.getSize() / KILOBYTE_CONVERSION_CONSTANT, filePath);

            //FileMetaData 저장
            FileMetadata savedMetadata = fileMetadataRepository.save(fileMetadata);

            //DTO로 변환 후 반환
            return APIResponse.success(SuccessCode.OK, FileMetadataDto.toDto(savedMetadata));

        } catch (Exception e) {
            //저장 실패시 S3에서 삭제
            s3ClientService.deleteFile(s3Key);
            throw new BusinessException(e.getMessage(), ErrorCode.FILE_UPLOAD_FAIL_ERROR);
        }
    }

    /**
     * 2-1. 파일 조회(단일)
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return FileMetadataDto 파일 메타데이터를 담은 응답 객체
     */
    @Transactional(readOnly = true)
    public FileMetadataDto getFile(String fileCategory, Long referenceId) {
        //파일 카테고리명으로 파일 카테고리 enum 가져오기
        FileCategory categoryEnum = FileCategory.valueOf(fileCategory);

        //해당 파일 카테고리와 참조 id로 entity를 가져온다.
        //파일이 존재하지 않아도 예외를 던지면 안됨.
        FileMetadata fileMetadataEntity = fileMetadataRepository.findTopByCategoryAndReferenceIdOrderByCreatedAtDesc(categoryEnum, referenceId)
                .orElse(null);

        //dto 변환 후 반환
        return FileMetadataDto.toDto(fileMetadataEntity);
    }

    /**
     * 2-2. 파일 조회(리스트)
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return List<FileMetadataDto> 파일 메타데이터 목록을 담은 응답 객체
     */
    @Transactional(readOnly = true)
    public List<FileMetadataDto> getFiles(String fileCategory, Long referenceId) {
        //파일 카테고리명으로 파일 카테고리 enum 가져오기
        FileCategory categoryEnum = FileCategory.valueOf(fileCategory);

        //해당 파일 카테고리와 참조 id로 entity를 가져온다.
        List<FileMetadata> fileMetadataEntities = fileMetadataRepository.findAllByCategoryAndReferenceId(categoryEnum, referenceId);

        //entity를 dto에 담는다.
        List<FileMetadataDto> fileMetadataDtos = new ArrayList<>();
        for (FileMetadata fileMetadata : fileMetadataEntities) {
            FileMetadataDto dto = FileMetadataDto.toDto(fileMetadata);
            fileMetadataDtos.add(dto);
        }

        //반환
        return fileMetadataDtos;
    }
}
