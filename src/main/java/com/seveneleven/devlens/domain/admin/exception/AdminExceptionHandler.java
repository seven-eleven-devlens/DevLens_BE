package com.seveneleven.devlens.domain.admin.exception;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminExceptionHandler {
    @ExceptionHandler(value = {CompanyDuplicatedException.class})
    public ResponseEntity<APIResponse<CompanyDto.CompanyResponse>> handleCompanyDuplicatedException(
            CompanyDuplicatedException e
    ) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(APIResponse.fail(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(value = {CompanyNotFoundException.class})
    public ResponseEntity<APIResponse<Exception>> handleCompanyNotFoundException(
            CompanyNotFoundException e
    ) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(APIResponse.fail(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(value = {ProjectHistoryNotFoundException.class})
    public ResponseEntity<APIResponse<Exception>> handleProjectHistoryNotFoundException(
            ProjectHistoryNotFoundException e
    ) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(APIResponse.fail(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(value = {ProjectNameDuplicatedException.class})
    public ResponseEntity<APIResponse<Exception>> handleProjectNameDuplicatedException(
            ProjectNameDuplicatedException e
    ) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(APIResponse.fail(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(value = {ProjectNotFoundException.class})
    public ResponseEntity<APIResponse<Exception>> handleProjectNotFoundException(
            ProjectNotFoundException e
    ) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(APIResponse.fail(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<APIResponse<Exception>> handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        return ResponseEntity
                .status(ErrorCode.ENTITY_NOT_FOUND.getStatus())
                .body(APIResponse.fail(ErrorCode.ENTITY_NOT_FOUND, e.getMessage()));
    }
}
