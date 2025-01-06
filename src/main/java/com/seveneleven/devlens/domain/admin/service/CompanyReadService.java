package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.db.CompanyRepository;
import com.seveneleven.devlens.domain.admin.db.CompanyResponseConverter;
import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.global.response.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyReadService {
    private final CompanyRepository companyRepository;
    private final CompanyResponseConverter companyResponseConverter;
    private final int pageSize = 20;
    /*
        함수명 : getCompanyDto
        함수 목적 : 회사 상세조회
     */
    @Transactional(readOnly = true)
    public CompanyDto.CompanyResponse getCompanyResponse(Long id) {
        return companyRepository.findById(id)
                .map(company -> {
                    if (company.getIsActive() == YN.N) {
                        throw new EntityNotFoundException(ErrorCode.COMPANY_IS_DEACTIVATED.getMessage());
                    }
                    return company;
                })
                .map(companyResponseConverter::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMPANY_IS_NOT_FOUND.getMessage()));
    }

    /*
        함수명 : getListOfCompanies
        함수 목적 : 회사 목록조회
    */
    @Transactional(readOnly = true)
    public Page<CompanyDto.CompanyResponse> getListOfCompanies(int page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("companyName").ascending());
        Page<Company> companyPage = companyRepository.findPage(pageable);
        return companyPage.map(companyResponseConverter::toDTO);
    }

    @Transactional(readOnly = true)
    public int getTotalPages() {
        long totalCount = companyRepository.count();
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}
