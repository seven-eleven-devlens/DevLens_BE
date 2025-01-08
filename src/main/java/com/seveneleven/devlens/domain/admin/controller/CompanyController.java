package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.service.CompanyCreateService;
import com.seveneleven.devlens.domain.admin.service.CompanyReadService;
import com.seveneleven.devlens.domain.admin.service.CompanyUpdateService;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyCreateService companyCreateService;
    private final CompanyReadService companyReadService;
    private final CompanyUpdateService companyUpdateService;

    /*
        함수명 : createCompany
        목적 : 회사 생성하여 db에 저장
     */
    @PostMapping("/new")
    public ResponseEntity<APIResponse<CompanyDto.CompanyResponse>> createCompany(
            @Valid @RequestBody CompanyDto.CompanyRequest companyRequest
    ) {
        var company = companyCreateService.createCompany(companyRequest);
        return ResponseEntity
                .status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED, company));
    }

    /*
        함수명 : readCompany
        목적 : 회사 상세 정보 조회
     */
    @GetMapping("/list/{companyId}")
    public ResponseEntity<APIResponse<CompanyDto.CompanyResponse>> readCompany(
            @PathVariable Long companyId
    ) {
        var company = companyReadService.getCompanyResponse(companyId);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, company));
    }

    /*
        함수명 : readCompanyList
        목적 : 회사 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<APIResponse<PaginatedResponse<CompanyDto.CompanyResponse>>> readCompanyList(
            @RequestParam(value = "page", required = true) Integer page
    ) {
        PaginatedResponse<CompanyDto.CompanyResponse> response = companyReadService.getListOfCompanies(page);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }

    /*
        함수명 : updateCompany
        목적 : 회사 상세 정보 수정
     */
    @PutMapping("/list/{id}")
    public ResponseEntity<APIResponse<CompanyDto.CompanyResponse>> updateCompany(
            @PathVariable Long id,
            @RequestBody CompanyDto.CompanyRequest companyRequest
    ) {
        return ResponseEntity
                .status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED, companyUpdateService.updateCompany(id, companyRequest)));
    }

    /*
        함수명 : deleteCompany
        목적 : 회사 상세 정보 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Object>> deleteCompany(
            @PathVariable Long id
    ) {
        companyUpdateService.deleteCompany(id);
        return ResponseEntity
                .status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }
}
