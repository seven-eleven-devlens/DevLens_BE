package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.db.CompanyRepository;
import com.seveneleven.devlens.domain.admin.db.CompanyResponseConverter;
import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.exception.CompanyNotFoundException;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Company;
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
    private final int  pageSize = 20;
    /*
        함수명 : getCompanyDto
        함수 목적 : 회사 상세조회
     */
    @Transactional(readOnly = true)
    public CompanyDto.CompanyResponse getCompanyResponse(Long id) {
        return companyRepository.findByIdAndIsActive(id,YN.Y)
                .map(companyResponseConverter::toDTO)
                .orElseThrow(CompanyNotFoundException::new);
    }

    /*
        함수명 : getListOfCompanies
        함수 목적 : 회사 목록조회
    */
    @Transactional(readOnly = true)
    public PaginatedResponse<CompanyDto.CompanyResponse> getListOfCompanies(int page) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("companyName").descending());
        Page<Company> companyPage = companyRepository.findByIsActive(pageable,YN.Y);
        if(companyPage.getContent().isEmpty()) {
            throw new CompanyNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(companyPage.map(companyResponseConverter::toDTO));
    }
}
