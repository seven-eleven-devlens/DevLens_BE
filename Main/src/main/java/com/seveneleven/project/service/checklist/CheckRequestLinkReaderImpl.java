package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.constant.LinkCategory;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.util.file.handler.LinkHandler;
import com.seveneleven.util.file.dto.LinkResponse;
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
public class CheckRequestLinkReaderImpl implements CheckRequestLinkReader {

    private final LinkHandler linkHandler;

    /**
     * 1. 체크 요청 링크 목록 조회
     * 함수명 : readCheckRequestLinks
     * @param checkRequest 체크 요청
     */
    @Override
    @Transactional(readOnly = true)
    public List<LinkResponse> readCheckRequestLinks(CheckRequest checkRequest) {
        //1. 카테고리와 체크요청 id로 모든 파일을 가져온다.
        List<Link> linkEntities = linkHandler.getLinks(LinkCategory.CHECK_APPROVE_REQUEST_LINK, checkRequest.getId());

        //2. 게시물의 링크 엔티티를 dto로 변환하기
        return Optional.ofNullable(linkEntities)
                .orElse(List.of())
                .stream()
                .map(LinkResponse::toDto)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    /**
     * 2. 체크 요청 반려 링크 목록 조회
     * 함수명 : readCheckRequestRejectLinks
     * @param checkRequest 체크 요청
     */
    @Override
    @Transactional(readOnly = true)
    public List<LinkResponse> readCheckRequestRejectLinks(CheckRequest checkRequest) {
        //1. 카테고리와 체크요청 id로 모든 파일을 가져온다.
        List<Link> linkEntities = linkHandler.getLinks(LinkCategory.CHECK_REJECTION_REASON_LINK, checkRequest.getId());

        //2. 게시물의 링크 엔티티를 dto로 변환하기
        return Optional.ofNullable(linkEntities)
                .orElse(List.of())
                .stream()
                .map(LinkResponse::toDto)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
