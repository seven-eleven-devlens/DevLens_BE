package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.util.file.dto.LinkResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CheckRequestLinkReader {

    //체크 요청 링크 목록 조회
    List<LinkResponse> readCheckRequestLinks(CheckRequest checkRequest);

    //체크 요청 반려 링크 목록 조회
    List<LinkResponse> readCheckRequestRejectLinks(CheckRequest checkRequest);
}
