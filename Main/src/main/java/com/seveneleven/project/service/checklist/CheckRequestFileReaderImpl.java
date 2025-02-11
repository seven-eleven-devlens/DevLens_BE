package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.util.file.dto.FileMetadataResponse;
import com.seveneleven.util.file.handler.FileHandler;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckRequestFileReaderImpl implements CheckRequestFileReader {
    private final FileHandler fileHandler;
    private final FileMetadataRepository fileMetadataRepository;

    /**
     * 1. 체크 요청 파일 목록 조회
     * 함수명 : readCheckRequestFile
     * @param checkRequest 체크 요청
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileMetadataResponse> readCheckRequestFiles(CheckRequest checkRequest){
        //카테고리와 체크요청id로 모든 파일을 가져온다.(페이지네이션 없음)
        List<FileMetadata> fileEntities = fileHandler.getFiles(FileCategory.CHECK_REQUEST_ATTACHMENT, checkRequest.getId());

        return Optional.ofNullable(fileEntities)
                .orElse(List.of())
                .stream()
                .map(FileMetadataResponse::toDto)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    /**
     * 2. 체크 요청 반려 파일 목록 조회
     * 함수명 : readCheckRequestRejectFiles
     * @param checkRequest 체크 요청
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileMetadataResponse> readCheckRequestRejectFiles(CheckRequest checkRequest){
        //카테고리와 체크요청id로 거절사유의 모든 파일을 가져온다.(페이지네이션 없음)
        List<FileMetadata> fileEntities = fileHandler.getFiles(FileCategory.CHECK_REJECTION_ATTACHMENT, checkRequest.getId());

        //entity를 dto에 담는다.
        return Optional.ofNullable(fileEntities)
                .orElse(List.of())
                .stream()
                .map(FileMetadataResponse::toDto)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
