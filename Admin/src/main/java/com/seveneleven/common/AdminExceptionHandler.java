package com.seveneleven.common;

import com.seveneleven.company.dto.PutCompany;
import com.seveneleven.company.exception.CompanyDuplicatedException;
import com.seveneleven.company.exception.CompanyNotFoundException;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.exception.ProjectHistoryNotFoundException;
import com.seveneleven.project.exception.ProjectNameDuplicatedException;
import com.seveneleven.project.exception.ProjectNotFoundException;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminExceptionHandler {
    @ExceptionHandler(value = {CompanyDuplicatedException.class, CompanyNotFoundException.class,
            ProjectHistoryNotFoundException.class, ProjectNameDuplicatedException.class, ProjectNotFoundException.class})
    public ResponseEntity<APIResponse<PutCompany.Response>> handleCompanyDuplicatedException(
            BusinessException e
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
