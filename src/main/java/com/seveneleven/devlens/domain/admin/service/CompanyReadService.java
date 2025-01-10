package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.admin.dto.GetCompany;
import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.exception.CompanyNotFoundException;
import com.seveneleven.devlens.domain.admin.repository.*;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.project.entity.Project;
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
    private final AdminProjectRepository adminProjectRepository;
    private final ProjectResponseConverter projectResponseConverter;
    private final GetCompanyResponseConverter getCompanyResponseConverter;

    /*
        함수명 : getCompanyDto
        함수 목적 : 회사 상세조회
     */
    @Transactional(readOnly = true)
    public GetCompany.Response getCompanyResponse(
            Long id, Integer page
    ) {
        Company company = companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("projectName").ascending());
        Page<Project> projectPage = adminProjectRepository.findByCustomerOrDeveloper(pageable, company, company);
        GetCompany.Response response = companyRepository
                .findByIdAndIsActive(id, YN.Y)
                .map(getCompanyResponseConverter::toDTO)
                .orElseThrow(CompanyNotFoundException::new);

        return response.addProjectList(response, PaginatedResponse.createPaginatedResponse(projectPage.map(projectResponseConverter::toDTO)));
    }

    /*
        함수명 : getListOfCompanies
        함수 목적 : 회사 목록조회
    */
    @Transactional(readOnly = true)
    public PaginatedResponse<CompanyDto.CompanyResponse> getListOfCompanies(
            Integer page
    ) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("companyName").descending());
        Page<Company> companyPage = companyRepository.findByIsActive(pageable, YN.Y);
        if (companyPage.getContent().isEmpty()) {
            throw new CompanyNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(companyPage.map(companyResponseConverter::toDTO));
    }
}