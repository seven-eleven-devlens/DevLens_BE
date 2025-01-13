package com.seveneleven.exception;

import static com.seveneleven.response.ErrorCode.COMPANY_IS_NOT_FOUND;

public class CompanyNotFoundException extends BusinessException {
    public CompanyNotFoundException(String message) {
        super(message, COMPANY_IS_NOT_FOUND);
    }

    public CompanyNotFoundException() {
        this(COMPANY_IS_NOT_FOUND.getMessage());
    }
}

