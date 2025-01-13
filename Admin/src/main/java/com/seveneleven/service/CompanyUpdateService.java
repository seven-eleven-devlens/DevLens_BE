package com.seveneleven.service;

import com.seveneleven.common.CheckCompanyValidity;
import com.seveneleven.dto.PutCompany;
import com.seveneleven.entity.member.Company;
import com.seveneleven.repository.CompanyRepository;
import com.seveneleven.repository.PutCompanyRequestConverter;
import com.seveneleven.repository.PutCompanyResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyUpdateService {
    private final CompanyRepository companyRepository;
    private final PutCompanyRequestConverter putCompanyRequestConverter;
    private final PutCompanyResponseConverter putCompanyResponseConverter;
    private final CheckCompanyValidity checkCompanyValidity;

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