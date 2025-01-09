package com.seveneleven.devlens.domain.admin.exception;

import com.seveneleven.devlens.global.exception.BusinessException;

import static com.seveneleven.devlens.global.response.ErrorCode.COMPANY_IS_NOT_FOUND;

public class CompanyNotFoundException extends BusinessException {
    public CompanyNotFoundException(String message) {
        super(message, COMPANY_IS_NOT_FOUND);
    }

    public CompanyNotFoundException() {
        this(COMPANY_IS_NOT_FOUND.getMessage());
    }
}

