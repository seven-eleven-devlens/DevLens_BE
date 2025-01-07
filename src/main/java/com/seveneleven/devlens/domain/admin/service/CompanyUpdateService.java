package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.common.CheckCompanyValidity;
import com.seveneleven.devlens.domain.admin.db.CompanyRepository;
import com.seveneleven.devlens.domain.admin.db.CompanyRequestConverter;
import com.seveneleven.devlens.domain.admin.db.CompanyResponseConverter;
import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.admin.exception.CompanyDuplicatedException;
import com.seveneleven.devlens.domain.member.entity.Company;
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
    public CompanyDto.CompanyResponse updateCompany(Long id, CompanyDto.CompanyRequest companyRequest) {
        //비활성화 및 존재 여부 확인
        checkCompanyValidity.checkCompanyExistsOrDeactivated(id);
        //회사 isActive N으로 변경
        companyRepository.deactivateCompany(id);
        try {
            //중복 회사 등록 번호 확인
            checkCompanyValidity.checkDuplicatedCompany(companyRequest.getBusinessRegistrationNumber());
        }catch(CompanyDuplicatedException e){
            //수정하려는 회사 정보가 중복된 회사이면 다시 활성화
            companyRepository.activateCompany(id);
            throw e;
        }
        //신규 데이터로 회사 생성
        Company company = companyRequestConverter.toEntity(companyRequest);
        return companyResponseConverter.toDTO(companyRepository.save(company));
    }

    @Transactional
    public void deleteCompany(Long id) {
        //비활성화 및 존재 여부 확인
        checkCompanyValidity.checkCompanyExistsOrDeactivated(id);
        //회사 isActive N으로 변경
        companyRepository.deactivateCompany(id);
    }


}