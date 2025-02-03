package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.constant.LinkCategory;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.util.file.Service.LinkService;
import com.seveneleven.util.file.dto.LinkResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckRequestLinkReaderImpl implements CheckRequestLinkReader {

    private final LinkService linkService;

    /**
     * 1. 체크 요청 링크 목록 조회
     * 함수명 : readCheckRequestLinks
     * @param checkRequest 체크 요청
     */
    @Override
    @Transactional(readOnly = true)
    public List<LinkResponse> readCheckRequestLinks(CheckRequest checkRequest) {
        //카테고리와 체크요청 id로 모든 파일을 가져온다.
        List<Link> linkEntities = linkService.getLinks(LinkCategory.CHECK_APPROVE_REQUEST_LINK, checkRequest.getId());

        //entity를 dto로 변환
        List<LinkResponse> linkResponses = new ArrayList<>();
        for (Link link : linkEntities) {
            LinkResponse linkResponse = LinkResponse.toDto(link);
            linkResponses.add(linkResponse);
        }

        return linkResponses;
    }

    /**
     * 2. 체크 요청 반려 링크 목록 조회
     * 함수명 : readCheckRequestRejectLinks
     * @param checkRequest 체크 요청
     */
    @Override
    @Transactional(readOnly = true)
    public List<LinkResponse> readCheckRequestRejectLinks(CheckRequest checkRequest) {
        //카테고리와 체크요청 id로 모든 파일을 가져온다.
        List<Link> linkEntities = linkService.getLinks(LinkCategory.CHECK_REJECTION_REASON_LINK, checkRequest.getId());

        //entity를 dto로 변환
        List<LinkResponse> linkResponses = new ArrayList<>();
        for (Link link : linkEntities) {
            LinkResponse linkResponse = LinkResponse.toDto(link);
            linkResponses.add(linkResponse);
        }

        return linkResponses;
    }
}
