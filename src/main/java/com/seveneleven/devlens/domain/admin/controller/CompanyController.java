package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.model.CompanyDto;
import com.seveneleven.devlens.domain.admin.service.CompanyCreateService;
import com.seveneleven.devlens.domain.admin.service.CompanyReadService;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyCreateService companyCreateService;
    private final CompanyReadService companyReadService;

    @PostMapping("/create")
    public APIResponse<CompanyDto> createCompany(@Valid @RequestBody CompanyDto companyDto) {
        CompanyDto company = companyCreateService.createCompany(companyDto);
        return APIResponse.create(company);
    }

    @GetMapping("/{companyId}")
    public APIResponse<CompanyDto> readCompany(@PathVariable Long companyId) {
        var company = companyReadService.getCompanyDto(companyId);
        return APIResponse.success(SuccessCode.OK, "회사 조회가 완료되었습니다.",company);
    }

    @GetMapping("")
    public APIResponse<List<CompanyDto>> readAllCompanies(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<CompanyDto> paging = this.companyReadService.getListOfCompanies(page);
        return APIResponse.success(SuccessCode.OK, paging.getContent());
    }

}
