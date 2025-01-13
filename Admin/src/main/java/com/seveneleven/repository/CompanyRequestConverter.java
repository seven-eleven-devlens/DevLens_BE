package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.CompanyDto;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyRequestConverter implements EntityConverter<CompanyDto.CompanyRequest, Company> {
    /*
        함수명 : toDto
        함수 목적 : entity 를 request 로 변환
     */
    @Override
    public CompanyDto.CompanyRequest toDTO(
            Company company
    ) {
        return new CompanyDto.CompanyRequest(
                company.getId(),
                company.getCompanyName(),
                company.getRepresentativeName(),
                company.getRepresentativeContact(),
                company.getRepresentativeEmail(),
                company.getAddress(),
                company.getBusinessType(),
                company.getBusinessRegistrationNumber(),
                company.getIsActive());
    }

    /*
        함수명 : toEntity
        함수 목적 : request 를 entity 로 변환
     */
    @Override
    public Company toEntity(
            CompanyDto.CompanyRequest companyResponse
    ) {
        return new Company(
                companyResponse.getId(),
                companyResponse.getCompanyName(),
                companyResponse.getRepresentativeName(),
                companyResponse.getRepresentativeContact(),
                companyResponse.getRepresentativeEmail(),
                companyResponse.getAddress(),
                companyResponse.getBusinessType(),
                companyResponse.getBusinessRegistrationNumber(),
                companyResponse.getIsActive());
    }

}
