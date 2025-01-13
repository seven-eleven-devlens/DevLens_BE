package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckRequestHistory;
import com.seveneleven.project.dto.PostProjectChecklistApplication;
import jakarta.servlet.http.HttpServletRequest;

public interface CheckRequestStore {

    CheckRequest checkRequestStore(
            PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    );

    CheckRequestHistory checkRequestHistoryStore(CheckRequest checkRequest);
}
