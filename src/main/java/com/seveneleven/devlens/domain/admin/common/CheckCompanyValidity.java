package com.seveneleven.devlens.domain.admin.common;

import com.seveneleven.devlens.domain.admin.exception.CompanyDuplicatedException;
import com.seveneleven.devlens.domain.admin.exception.CompanyNotFoundException;
import com.seveneleven.devlens.domain.admin.repository.CompanyRepository;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckCompanyValidity {
    private final CompanyRepository companyRepository;

    /*
        함수명 : checkDuplicatedCompany
        함수 목적 : 중복 회사 조회
     */
    public void checkDuplicatedCompanyBusinessRegistrationNumber(
            String businessRegistrationNumber
    ) {
        companyRepository.findByBusinessRegistrationNumberAndIsActive(businessRegistrationNumber, YN.Y)
                .ifPresent(company -> {
                    throw new CompanyDuplicatedException();
                });
    }

    /*
        함수명 : checkCompanyExistsOrDeactivated
        함수 목적 : 회사 존재여부 확인 및 비활성화 여부 확인
     */
    public Company checkCompanyExistsOrDeactivated(
            Long id
    ) {
        return companyRepository.findByIdAndIsActive(id, YN.Y)
                .orElseThrow(CompanyNotFoundException::new);
    }
}