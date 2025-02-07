package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.handler.FileHandler;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckRequestFileStoreImpl implements CheckRequestFileStore {
    private final FileHandler fileHandler;
    private final FileMetadataRepository fileMetadataRepository;

    private static final int MAX_FILE_COUNT = 10; //게시물별 최대 파일 수(10개)

    /**
     * 1. 체크 요청 파일 등록
     * 함수명 : checkRequestFileStore
     * @param files 등록할 파일
     * @param checkRequest 체크 요청
     * @param member 체크 요청 수행자
     */
    @Override
    @Transactional
    public void checkRequestFileStore(
            List<MultipartFile> files,
            CheckRequest checkRequest,
            Member member){

        //TODO) 1. 체크리스트 파일 업로드 권한 판별

        //2. 저장할 파일 갯수 + 현재 저장 갯수 >= 11인지 판별
        //현재 저장된 파일 갯수 확인
        Integer currentFileCnt = fileMetadataRepository.countByCategoryAndReferenceId(FileCategory.CHECK_REQUEST_ATTACHMENT, checkRequest.getId());
        if(currentFileCnt + files.size() > MAX_FILE_COUNT){
            throw new BusinessException(ErrorCode.FILE_QUANTITY_EXCEED_ERROR);
        }

        //3. 파일 업로드
        for(MultipartFile file : files){
            fileHandler.uploadFile(file, FileCategory.CHECK_REQUEST_ATTACHMENT, checkRequest.getId());
        }
    }

    /**
     * 2. 체크 요청 반려 파일 등록
     * 함수명 : checkRequestRejectFileStore
     * @param files 등록할 파일
     * @param checkRequest 반려할 체크 요청
     * @param member 체크 요청 반려 수행자
     */
    @Override
    @Transactional
    public void checkRequestRejectFileStore(
            List<MultipartFile> files,
            CheckRequest checkRequest,
            Member member) {

        //TODO) 1. 체크리스트 반려 파일 업로드 권한 판별

        //2. 저장할 파일 갯수 + 현재 저장 갯수 >= 11인지 판별
        //현재 저장된 파일 갯수 확인
        Integer currentFileCnt = fileMetadataRepository.countByCategoryAndReferenceId(FileCategory.CHECK_REJECTION_ATTACHMENT, checkRequest.getId());
        if(currentFileCnt + files.size() > MAX_FILE_COUNT){
            throw new BusinessException(ErrorCode.FILE_QUANTITY_EXCEED_ERROR);
        }

        //3. 파일 업로드
        for(MultipartFile file : files){
            fileHandler.uploadFile(file, FileCategory.CHECK_REJECTION_ATTACHMENT, checkRequest.getId());
        }
    }
}
