package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.model.CompanyDto;
import com.seveneleven.devlens.domain.member.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyConverter implements EntityConverter<CompanyDto, Company> {
    @Override
    public CompanyDto toDTO(Company company) {
        return CompanyDto.builder()
                .companyName(company.getCompanyName())
                .representativeName(company.getRepresentativeName())
                .representativeEmail(company.getRepresentativeEmail())
                .representativeContact(company.getRepresentativeContact())
                .address(company.getAddress())
                .businessType(company.getBusinessType())
                .businessRegistrationNumber(company.getBusinessRegistrationNumber())
                .representativeImageExists(company.getRepresentativeImageExists())
                .activeStatus(company.getActiveStatus())
                .build();
    }

    @Override
    public Company toEntity(CompanyDto companyDto) {
        return Company.builder()
                .companyName(companyDto.getCompanyName())
                .representativeName(companyDto.getRepresentativeName())
                .representativeEmail(companyDto.getRepresentativeEmail())
                .representativeContact(companyDto.getRepresentativeContact())
                .address(companyDto.getAddress())
                .businessType(companyDto.getBusinessType())
                .businessRegistrationNumber(companyDto.getBusinessRegistrationNumber())
                .representativeImageExists(companyDto.getRepresentativeImageExists())
                .activeStatus(companyDto.getActiveStatus())
                .build();
    }
}
