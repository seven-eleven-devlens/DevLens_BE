package com.seveneleven.exception;

import static com.seveneleven.response.ErrorCode.PROJECT_DUPLICATED_NAME;

public class ProjectNameDuplicatedException extends BusinessException {

    public ProjectNameDuplicatedException(String message) {
        super(message, PROJECT_DUPLICATED_NAME);
    }

    public ProjectNameDuplicatedException() {
        this(PROJECT_DUPLICATED_NAME.getMessage());
    }
}
