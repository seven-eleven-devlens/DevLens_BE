package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.admin.service.CompanyServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyServiceImpl companyService;

    @PostMapping("/create")
    public void createCompany(@Valid @RequestBody CompanyDto companyDto) {
        companyService.createCompany(companyDto);
    }
}
