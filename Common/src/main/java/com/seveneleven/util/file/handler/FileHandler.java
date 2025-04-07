package com.seveneleven.util.file.handler;

import com.seveneleven.exception.BusinessException;
import com.seveneleven.util.file.FileValidator;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FileHandler {
    private final FileMetadataRepository fileMetadataRepository;
    private final S3ClientHandler s3ClientHandler;
    private final GcsClientHandler gcsClientHandler;

    private static final double KILOBYTE_CONVERSION_CONSTANT = 1024.0;

    /**
     * 1. 파일 업로드
     * 함수명 : uploadFile
     * @param file 업로드할 파일
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return FileMetadataDto 업로드한 파일 메타데이터
     */
    //S3
//    @Transactional
//    public FileMetadata uploadFile(MultipartFile file, FileCategory fileCategory, Long referenceId) {
//        //1. 파일 검증
//        FileValidator.validateFile(file, fileCategory);
//
//        //2. 고유 파일 이름(UUID) 및 S3 키 생성
//        //파일명이 없거나 비어있으면 UNKNOWN-FILE로 설정
//        String originalFilename = StringUtils.isEmpty(file.getOriginalFilename()) ? "UNKNOWN-FILE" : file.getOriginalFilename();
//
//        //UUID 생성
//        String uniqueFileName = s3ClientHandler.generateUniqueFileName(originalFilename);
//        //S3 키 생성
//        String s3Key = s3ClientHandler.generateS3Key(fileCategory.name(), referenceId, uniqueFileName);
//
//        //3. S3 업로드 및 FileMetadata 데이터 생성
//        String filePath = null;
//        try {
//            //S3 업로드
//            filePath = s3ClientHandler.uploadFile(file, s3Key);
//
//            //entity 생성자 호출
//            FileMetadata fileMetadata = FileMetadata.registerFile(fileCategory, referenceId,
//                    file.getOriginalFilename(), uniqueFileName, file.getContentType(),
//                    file.getOriginalFilename().substring(originalFilename.lastIndexOf('.') + 1),
//                    file.getSize() / KILOBYTE_CONVERSION_CONSTANT, filePath);
//
//            //FileMetaData 저장
//            FileMetadata savedMetadata = fileMetadataRepository.save(fileMetadata);
//
//            //DTO로 변환 후 반환
//            return savedMetadata;
//
//        } catch (Exception e) {
//            //저장 실패시 S3에서 삭제
//            s3ClientHandler.deleteFile(s3Key);
//            throw new BusinessException(e.getMessage(), ErrorCode.FILE_UPLOAD_FAIL_ERROR);
//        }
//    }

    //GCS
    @Transactional
    public FileMetadata uploadFile(MultipartFile file, FileCategory fileCategory, Long referenceId) {
        //1. 파일 검증
        FileValidator.validateFile(file, fileCategory);

        //2. 고유 파일 이름(UUID) 및 GCS 키 생성
        //파일명이 없거나 비어있으면 UNKNOWN-FILE로 설정
        String originalFilename = StringUtils.isEmpty(file.getOriginalFilename()) ? "UNKNOWN-FILE" : file.getOriginalFilename();

        //UUID 생성
        String uniqueFileName = gcsClientHandler.generateUniqueFileName(originalFilename);
        //GCS 키 생성
        String gcsKey = gcsClientHandler.generateGcsKey(fileCategory.name(), referenceId, uniqueFileName);

        //3. GCS 업로드 및 FileMetadata 데이터 생성
        try {
            String filePath = null;
            //GCS 업로드
            filePath = gcsClientHandler.uploadFile(file, gcsKey);

            //entity 생성자 호출
            FileMetadata fileMetadata = FileMetadata.registerFile(fileCategory, referenceId,
                    file.getOriginalFilename(), uniqueFileName, file.getContentType(),
                    file.getOriginalFilename().substring(originalFilename.lastIndexOf('.') + 1),
                    file.getSize() / KILOBYTE_CONVERSION_CONSTANT, filePath);

            //FileMetaData 저장
            FileMetadata savedMetadata = fileMetadataRepository.save(fileMetadata);

            //DTO로 변환 후 반환
            return savedMetadata;

        } catch (Exception e) {
            //저장 실패시 GCS 버킷에서 삭제
            gcsClientHandler.deleteFile(gcsKey);

            throw new BusinessException(e.getMessage(), ErrorCode.FILE_UPLOAD_FAIL_ERROR);
        }
    }

    /**
     * 2-1. 파일 조회(단일)
     * 함수명 : getFile
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return FileMetadataDto 파일 메타데이터를 담은 응답 객체
     */
    @Transactional(readOnly = true)
    public FileMetadata getFile(FileCategory fileCategory, Long referenceId) {
        //해당 파일 카테고리와 참조 id로 entity를 가져온다.
        //파일이 존재하지 않아도 예외를 던지면 안됨.
        FileMetadata fileMetadataEntity = fileMetadataRepository.findByCategoryAndReferenceId(fileCategory, referenceId)
                .orElse(null);

        return fileMetadataEntity;
    }

    /**
     * 2-2. 파일 조회(리스트)
     * 함수명 : getFiles
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return List<FileMetadataDto> 파일 메타데이터 목록을 담은 응답 객체
     */
    @Transactional(readOnly = true)
    public List<FileMetadata> getFiles(FileCategory fileCategory, Long referenceId) {

        //해당 파일 카테고리와 참조 id로 entity를 가져온다.
        List<FileMetadata> fileMetadataEntities = fileMetadataRepository.findAllByCategoryAndReferenceId(fileCategory, referenceId);

        return fileMetadataEntities;
    }

    /**
     * 3-1. 파일 삭제(카테고리, 참조ID)
     * 함수명 : deleteFile
     * @param fileCategory 파일 카테고리
     * @param referenceId 참조 ID
     */
    @Transactional
    public FileMetadata deleteFile(FileCategory fileCategory, Long referenceId) {
        //카테고리와 참조 ID로 검색후 존재 유무 판별
        FileMetadata toDeleteData = fileMetadataRepository.findByCategoryAndReferenceId(fileCategory, referenceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_FOUND_ERROR));

        fileMetadataRepository.delete(toDeleteData);

        return toDeleteData;
    }

    /**
     * 3-2. 파일 삭제(파일메타데이터 id)
     * 함수명 : deleteFileById
     * @param fileId 파일메타데이터 id
     */
    @Transactional
    public FileMetadata deleteFileById(Long fileId){
        //해당 파일 존재 유무 판별
        FileMetadata toDeleteData = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_FOUND_ERROR));

        fileMetadataRepository.deleteById(toDeleteData.getId());

        return toDeleteData;
    }
}
