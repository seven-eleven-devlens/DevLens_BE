package com.seveneleven.service;

import com.seveneleven.dto.GetCompanies;
import com.seveneleven.dto.GetCompanyDetail;
import com.seveneleven.dto.PaginatedResponse;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.entity.project.Project;
import com.seveneleven.exception.CompanyNotFoundException;
import com.seveneleven.repository.*;
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
    private final PutCompanyResponseConverter putCompanyResponseConverter;
    private final int PAGE_SIZE = 20;
    private final AdminProjectRepository adminProjectRepository;
    private final ProjectResponseConverter projectResponseConverter;
    private final GetCompanyDetailResponseConverter getCompanyDetailResponseConverter;
    private final GetCompaniesResponseConverter getCompaniesResponseConverter;

    /*
        함수명 : getCompanyDto
        함수 목적 : 회사 상세조회
     */
    @Transactional(readOnly = true)
    public GetCompanyDetail.Response getCompanyResponse(
            Long id, Integer page
    ) {
        //참여 프로젝트 조회를 위한 회사 조회
        Company company = companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        //참여 프로젝트 조회 및 페이지 생성
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("projectName").ascending());
        Page<Project> projectPage = adminProjectRepository.findByCustomerOrDeveloper(pageable, company, company);
        //회사 조회
        GetCompanyDetail.Response response = companyRepository
                .findByIdAndIsActive(id, YN.Y)
                .map(getCompanyDetailResponseConverter::toDTO)
                .orElseThrow(CompanyNotFoundException::new);

        return GetCompanyDetail.Response.addProjectList(response, PaginatedResponse.createPaginatedResponse(projectPage.map(projectResponseConverter::toDTO)));
    }

    /*
        함수명 : getListOfCompanies
        함수 목적 : 회사 목록조회
    */
    @Transactional(readOnly = true)
    public PaginatedResponse<GetCompanies.Response> getListOfCompanies(
            Integer page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("companyName").ascending());
        Page<Company> companyPage = companyRepository.findByIsActive(pageable, YN.Y);
        if (companyPage.getContent().isEmpty()) {
            throw new CompanyNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(companyPage.map(getCompaniesResponseConverter::toDTO));
    }
    /*
            함수명 : searchCompaniesByName
            함수 목적 : 회사 검색
     */
    @Transactional(readOnly = true)
    public PaginatedResponse<GetCompanyDetail.Response> searchCompaniesByName(
            String name, Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("companyName").ascending());
        Page<Company> companyPage = companyRepository.findByIsActiveAndCompanyNameContainingIgnoreCase(YN.Y, pageable, name);
        return PaginatedResponse.createPaginatedResponse(companyPage.map(getCompanyDetailResponseConverter::toDTO));
    }
}