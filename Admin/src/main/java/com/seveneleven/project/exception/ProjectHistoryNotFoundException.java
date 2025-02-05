package com.seveneleven.project.exception;

import com.seveneleven.exception.BusinessException;

import static com.seveneleven.response.ErrorCode.PROJECT_HISTORY_NOT_FOUND_EXCEPTION;

public class ProjectHistoryNotFoundException extends BusinessException {
    public ProjectHistoryNotFoundException(String message) {
        super(message, PROJECT_HISTORY_NOT_FOUND_EXCEPTION);
    }

    public ProjectHistoryNotFoundException() {
        this(PROJECT_HISTORY_NOT_FOUND_EXCEPTION.getMessage());
    }
}
