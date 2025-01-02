package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.model.CompanyDto;
import com.seveneleven.devlens.domain.admin.service.CompanyServiceImpl;
import com.seveneleven.devlens.domain.member.entity.Company;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyServiceImpl companyService;

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyDto companyDto) {
        var company = companyService.createCompany(companyDto);
        return ResponseEntity.ok()
                .body(company);
    }

    @GetMapping("/read/{companyId}")

    public ResponseEntity<CompanyDto> readCompany(@PathVariable Long companyId) {
        var company = companyService.getCompanyDto(companyId);
        return ResponseEntity.ok()
                .body(company);
    }

}
