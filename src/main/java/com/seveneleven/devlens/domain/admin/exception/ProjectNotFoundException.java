package com.seveneleven.devlens.domain.admin.exception;

import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.ErrorCode;

public class ProjectNotFoundException extends BusinessException {
    public ProjectNotFoundException(String message) {
        super(message, ErrorCode.PROJECT_NOT_FOUND);
    }

    public ProjectNotFoundException() {
      this(ErrorCode.PROJECT_NOT_FOUND.getMessage());
    }
}
