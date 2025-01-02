package com.seveneleven.devlens.global.util.file.Service;

import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.ErrorCode;
import com.seveneleven.devlens.global.util.file.FileValidator;
import com.seveneleven.devlens.global.util.file.dto.FileMetadataDto;
import com.seveneleven.devlens.global.util.file.entity.FileMetadata;
import com.seveneleven.devlens.global.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMetadataRepository fileMetadataRepository;
    private final S3ClientService s3ClientService;

    /**
     * 1. 파일 업로드
     * @param file 업로드할 파일
     * @param uploaderId 업로더 id
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return FileMetadataDto 업로드한 파일 메타데이터
     */
    @Transactional
    public APIResponse uploadFile(MultipartFile file, Long uploaderId, String fileCategory, Long referenceId) throws Exception {
        //1. 파일 검증
        FileValidator.validateFile(file, fileCategory);

        //2. 고유 파일 이름(UUID) 및 S3 키 생성
        //파일명이 없거나 비어있으면 Unknown-File로 설정
        String originalFilename = (file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
                ? file.getOriginalFilename() : "Unknown-File";
        //UUID 생성
        String uniqueFileName = s3ClientService.generateUniqueFileName(originalFilename);
        //S3 키 생성
        String s3Key = s3ClientService.generateS3Key(uploaderId, fileCategory, uniqueFileName);

        //3. S3 업로드 및 FileMetadata 데이터 생성
        String filePath = null;
        try{
            //S3 업로드
            filePath = s3ClientService.uploadFile(file, s3Key);

            //업로드하는 파일 메타데이터 생성
            FileMetadata metadata = FileMetadata.builder()
                    .fileCategory(fileCategory)
                    .fileDisplayTitle(file.getOriginalFilename())
                    .fileTitle(uniqueFileName)
                    .writtenBy(uploaderId) //TODO) Audit
                    .writtenAt(LocalDateTime.now()) //TODO) Audit
                    .contentType(file.getContentType())
                    .fileFormat(file.getOriginalFilename().substring(originalFilename.lastIndexOf('.') + 1))
                    .fileSize(file.getSize() / 1024.0) // KB
                    .filePath(filePath)
                    .referenceId(referenceId) // 필요 시 설정
                    .createdBy(1L) //TODO) Audit
                    .createdAt(LocalDateTime.now()) //TODO) Audit
                    .build();

            //FileMetaData 저장
            FileMetadata savedMetadata = fileMetadataRepository.save(metadata);

            //DTO로 변환 후 반환
            return APIResponse.create(FileMetadataDto.toDto(savedMetadata));

        } catch (Exception e){
            //저장 실패시 S3에서 삭제
            s3ClientService.deleteFile(s3Key);

            throw new BusinessException(e.getMessage(), ErrorCode.S3_UPLOAD_FAIL_ERROR);
        }
    }

}
