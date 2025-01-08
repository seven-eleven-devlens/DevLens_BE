package com.seveneleven.devlens.domain.admin.exception;

import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.ErrorCode;

public class ProjectNameDuplicatedException extends BusinessException {

    public ProjectNameDuplicatedException(String message) {
        super(message,ErrorCode.PROJECT_DUPLICATED_NAME);
    }

    public ProjectNameDuplicatedException() {
        this(ErrorCode.PROJECT_DUPLICATED_NAME.getMessage());
    }
}
