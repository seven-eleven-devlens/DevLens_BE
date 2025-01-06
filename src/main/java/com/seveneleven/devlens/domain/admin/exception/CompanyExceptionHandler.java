package com.seveneleven.devlens.domain.admin.exception;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.global.response.APIResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CompanyExceptionHandler {
    @ExceptionHandler(value = {CompanyDuplicatedException.class})
    public APIResponse<CompanyDto.CompanyResponse> handleCompanyDuplicatedException(CompanyDuplicatedException e) {
        return APIResponse.fail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {CompanyAlreadyDeactivatedException.class})
    public APIResponse<CompanyDto.CompanyResponse> handleCompanyAlreadyDeactivatedException(CompanyAlreadyDeactivatedException e) {
        return APIResponse.fail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = {CompanyNotFoundException.class})
    public APIResponse<Exception> handleCompanyNotFoundException(CompanyNotFoundException e) {
        return APIResponse.fail(e.getErrorCode(), e.getMessage());
    }
}
