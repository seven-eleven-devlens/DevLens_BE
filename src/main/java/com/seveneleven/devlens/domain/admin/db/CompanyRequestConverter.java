package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.member.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyRequestConverter implements EntityConverter<CompanyDto.CompanyRequest, Company> {
    @Override
    public CompanyDto.CompanyRequest toDTO(Company company) {
        return new CompanyDto.CompanyRequest(
                company.getId(),
                company.getCompanyName(),
                company.getRepresentativeName(),
                company.getRepresentativeContact(),
                company.getRepresentativeEmail(),
                company.getAddress(),
                company.getBusinessType(),
                company.getBusinessRegistrationNumber(),
                company.getRepresentativeImageExists(),
                company.getIsActive());
    }

    @Override
    public Company toEntity(CompanyDto.CompanyRequest companyResponse) {
        return new Company(
                companyResponse.getId(),
                companyResponse.getCompanyName(),
                companyResponse.getRepresentativeName(),
                companyResponse.getRepresentativeContact(),
                companyResponse.getRepresentativeEmail(),
                companyResponse.getAddress(),
                companyResponse.getBusinessType(),
                companyResponse.getBusinessRegistrationNumber(),
                companyResponse.getRepresentativeImageExists(),
                companyResponse.getIsActive());
    }

}
