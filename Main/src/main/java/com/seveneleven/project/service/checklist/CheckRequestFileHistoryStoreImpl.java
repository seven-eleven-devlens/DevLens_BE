package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.FileMetadataHistory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.repository.FileMetadataHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Slf4j
@RequiredArgsConstructor
public class CheckRequestFileHistoryStoreImpl implements CheckRequestFileHistoryStore {

    private final MemberRepository memberRepository;
    private final CheckRequestRepository checkRequestRepository;
    private final FileMetadataHistoryRepository fileMetadataHistoryRepository;

    /**
     * 1. 체크 요청 파일 등록 이력 저장
     * 함수명 : saveRequestFileUploadHistory
     * @param uploadedFileEntity 업로드된 파일메타데이터 엔티티
     * @param registrant 등록 이력 수행자
     */

    @Override
    @Transactional
    public void saveRequestFileUploadHistory(FileMetadata uploadedFileEntity, Member registrant) {
        //1. 파일 업로더 정보 가져오기
        Member uploaderEntity = memberRepository.findById(uploadedFileEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 체크 요청 정보 가져오기
        CheckRequest checkRequestEntity = checkRequestRepository.findById(uploadedFileEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECK_REQUEST_NOT_FOUND));

        //3. 이력 엔티티 생성
        FileMetadataHistory uploadHistoryEntity = FileMetadataHistory.createRegisterHistory(
                uploadedFileEntity.getCategory(),
                uploadedFileEntity.getReferenceId(),
                checkRequestEntity.getTitle(),
                uploadedFileEntity.getDisplayTitle(),
                uploadedFileEntity.getTitle(),
                uploaderEntity.getEmail(),
                uploaderEntity.getName(),
                uploaderEntity.getRole(),
                uploadedFileEntity.getCreatedAt(),
                uploadedFileEntity.getContentType(),
                uploadedFileEntity.getFileFormat(),
                uploadedFileEntity.getFileSize(),
                uploadedFileEntity.getFilePath(),
                registrant.getName(),
                registrant.getEmail(),
                registrant.getRole()
        );

        //5. 이력
        fileMetadataHistoryRepository.save(uploadHistoryEntity);
    }

    /**
     * 2. 체크 요청 반려 파일 등록
     * 함수명 : saveRejectionFileUploadHistory
     * @param uploadedFileEntity 업로드된 파일메타데이터 엔티티
     * @param registrant 등록 이력 수행자 id
     */

    @Override
    @Transactional
    public void saveRejectionFileUploadHistory(FileMetadata uploadedFileEntity, Member registrant) {
        //1. 파일 업로더 정보 가져오기
        Member uploaderEntity = memberRepository.findById(uploadedFileEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 체크 요청 정보 가져오기
        CheckRequest checkRequestEntity = checkRequestRepository.findById(uploadedFileEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECK_REQUEST_NOT_FOUND));

        //3. 이력 엔티티 생성
        FileMetadataHistory uploadHistoryEntity = FileMetadataHistory.createRegisterHistory(
                uploadedFileEntity.getCategory(),
                uploadedFileEntity.getReferenceId(),
                checkRequestEntity.getTitle(),
                uploadedFileEntity.getDisplayTitle(),
                uploadedFileEntity.getTitle(),
                uploaderEntity.getEmail(),
                uploaderEntity.getName(),
                uploaderEntity.getRole(),
                uploadedFileEntity.getCreatedAt(),
                uploadedFileEntity.getContentType(),
                uploadedFileEntity.getFileFormat(),
                uploadedFileEntity.getFileSize(),
                uploadedFileEntity.getFilePath(),
                registrant.getName(),
                registrant.getEmail(),
                registrant.getRole()
        );

        //5. 이력 엔티티 저장
        fileMetadataHistoryRepository.save(uploadHistoryEntity);
    }
}
