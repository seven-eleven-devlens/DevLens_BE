package com.seveneleven.devlens.domain.admin.exception;

import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.ErrorCode;

import static com.seveneleven.devlens.global.response.ErrorCode.COMPANY_IS_DEACTIVATED;

public class CompanyAlreadyDeactivatedException extends BusinessException {
    public CompanyAlreadyDeactivatedException(String message) {
        super(message, COMPANY_IS_DEACTIVATED);
    }

    public CompanyAlreadyDeactivatedException() {
        this(COMPANY_IS_DEACTIVATED.getMessage());
    }
}
