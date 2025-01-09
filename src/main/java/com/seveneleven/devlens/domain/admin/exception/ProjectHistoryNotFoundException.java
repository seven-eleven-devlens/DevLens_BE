package com.seveneleven.devlens.domain.admin.exception;

import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.ErrorCode;

public class ProjectHistoryNotFoundException extends BusinessException {
    public ProjectHistoryNotFoundException(String message) {
        super(message, ErrorCode.PROJECT_HISTORY_NOT_FOUND_EXCEPTION);
    }

    public ProjectHistoryNotFoundException() {
        this(ErrorCode.PROJECT_HISTORY_NOT_FOUND_EXCEPTION.getMessage());
    }
}
