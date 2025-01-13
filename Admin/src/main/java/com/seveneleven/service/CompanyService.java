package com.seveneleven.service;

import com.seveneleven.common.CheckCompanyValidity;
import com.seveneleven.dto.*;
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
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final PutCompanyResponseConverter putCompanyResponseConverter;
    private final int PAGE_SIZE = 20;
    private final AdminProjectRepository adminProjectRepository;
    private final ProjectResponseConverter projectResponseConverter;
    private final GetCompanyDetailResponseConverter getCompanyDetailResponseConverter;
    private final GetCompaniesResponseConverter getCompaniesResponseConverter;
    private final PutCompanyRequestConverter putCompanyRequestConverter;
    private final CheckCompanyValidity checkCompanyValidity;
    private final PostCompanyRequestConverter postCompanyRequestConverter;
    private final PostCompanyResponseConverter postCompanyResponseConverter;

    /*
        함수명 : createCompany
        함수 목적 : 함수 정보 저장
     */
    public PostCompany.Response createCompany(
            PostCompany.Request companyRequest
    ) {
        //사업자 등록번호 중복 조회
        checkCompanyValidity.checkDuplicatedCompanyBusinessRegistrationNumber(companyRequest.getBusinessRegistrationNumber());
        return postCompanyResponseConverter.toDTO(companyRepository.save(postCompanyRequestConverter.toEntity(companyRequest)));
    }

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

    @Transactional
    public PutCompany.Response updateCompany(
            Long id,
            PutCompany.Request request
    ) {
        //비활성화 및 존재 여부 확인
        Company oldCompany = checkCompanyValidity.checkCompanyExistsOrDeactivated(id);
        //회사 isActive N으로 변경
        oldCompany.deleteCompany();
        //중복 회사 등록 번호 확인
        checkCompanyValidity.checkDuplicatedCompanyBusinessRegistrationNumber(request.getBusinessRegistrationNumber());
        //신규 데이터로 회사 생성
        Company company = putCompanyRequestConverter.toEntity(request);
        return putCompanyResponseConverter.toDTO(companyRepository.save(company));
    }

    @Transactional
    public void deleteCompany(
            Long id
    ) {
        //비활성화 및 존재 여부 확인
        Company company = checkCompanyValidity.checkCompanyExistsOrDeactivated(id);
        //회사 isActive N으로 변경
        company.deleteCompany();
    }
}