package com.seveneleven.service;

import com.seveneleven.common.CheckCompanyValidity;
import com.seveneleven.dto.CompanyDto;
import com.seveneleven.entity.member.Company;
import com.seveneleven.repository.CompanyRepository;
import com.seveneleven.repository.CompanyRequestConverter;
import com.seveneleven.repository.CompanyResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyUpdateService {
    private final CompanyRepository companyRepository;
    private final CompanyRequestConverter companyRequestConverter;
    private final CompanyResponseConverter companyResponseConverter;
    private final CheckCompanyValidity checkCompanyValidity;

    @Transactional
    public CompanyDto.CompanyResponse updateCompany(
            Long id,
            CompanyDto.CompanyRequest companyRequest
    ) {
        //비활성화 및 존재 여부 확인
        Company oldCompany = checkCompanyValidity.checkCompanyExistsOrDeactivated(id);
        //회사 isActive N으로 변경
        oldCompany.deleteCompany();
        //중복 회사 등록 번호 확인
        checkCompanyValidity.checkDuplicatedCompanyBusinessRegistrationNumber(companyRequest.getBusinessRegistrationNumber());
        //신규 데이터로 회사 생성
        Company company = companyRequestConverter.toEntity(companyRequest);
        return companyResponseConverter.toDTO(companyRepository.save(company));
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