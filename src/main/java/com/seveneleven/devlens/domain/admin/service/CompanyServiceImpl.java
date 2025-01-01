package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.db.CompanyConverter;
import com.seveneleven.devlens.domain.admin.model.CompanyDto;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.admin.exception.CompanyDuplicatedException;
import com.seveneleven.devlens.domain.admin.db.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl {
    private final CompanyRepository companyRepository;

    private final CompanyConverter companyConverter;

    /*
        함수명 : createCompany
        함수 목적 : 함수 정보 저장
     */
    public Company createCompany(CompanyDto companyDto) {
        try {
            //사업자 등록번호 중복 조회
            checkDuplicatedCompany(companyDto.getBusinessRegistrationNumber());
            //회사 저장, 대표 이미지 존재 여부는 파일 등록 시 변경
            var company = Company.builder()
                    .companyName(companyDto.getCompanyName())
                    .representativeName(companyDto.getRepresentativeName())
                    .representativeEmail(companyDto.getRepresentativeEmail())
                    .representativeContact(companyDto.getRepresentativeContact())
                    .address(companyDto.getAddress())
                    .businessType(companyDto.getBusinessType())
                    .businessRegistrationNumber(companyDto.getBusinessRegistrationNumber())
                    .representativeImageExists(false)
                    .activeStatus(true)
                    .build();

            return companyRepository.save(company);
        } catch (CompanyDuplicatedException e) {
            throw e;
        }
    }

    /*
        함수명 : checkDuplicatedCompany
        함수 목적 : 회사 중복 조회
     */
    private void checkDuplicatedCompany(String businessRegistrationNumber) {
        Company foundCompany = companyRepository.findByBusinessRegistrationNumber(businessRegistrationNumber);
        if (foundCompany != null) {
            throw new CompanyDuplicatedException();
        }
    }
}
