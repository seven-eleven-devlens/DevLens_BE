package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.db.CompanyConverter;
import com.seveneleven.devlens.domain.admin.db.CompanyRepository;
import com.seveneleven.devlens.domain.admin.model.CompanyDto;
import com.seveneleven.devlens.domain.member.entity.Company;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyReadService {
    private final CompanyRepository companyRepository;

    private final CompanyConverter companyConverter;

    /*
        함수명 : getCompanyDto
        함수 목적 : 회사 상세조회
     */
    @Transactional(readOnly = true)
    public CompanyDto getCompanyDto(Long id) {
        var company = companyRepository.findById(id)
                .map(companyConverter::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("회사 정보를 찾을 수 없습니다."));

        return company.returnCompanyStatus();
    }

    @Transactional(readOnly = true)
    public Page<CompanyDto> getListOfCompanies(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Company> companyPage = companyRepository.findAll(pageable);
        return companyPage.map(companyConverter::toDTO);
    }
}
