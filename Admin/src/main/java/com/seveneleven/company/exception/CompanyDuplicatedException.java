package com.seveneleven.company.exception;

import com.seveneleven.exception.BusinessException;

import static com.seveneleven.response.ErrorCode.COMPANY_DUPLICATED_NUMBER;

public class CompanyDuplicatedException extends BusinessException {
    public CompanyDuplicatedException(String message) {
        super(message, COMPANY_DUPLICATED_NUMBER);
    }

    public CompanyDuplicatedException() {
        this(COMPANY_DUPLICATED_NUMBER.getMessage());
    }
}
