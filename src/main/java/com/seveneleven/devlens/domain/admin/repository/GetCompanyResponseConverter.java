package com.seveneleven.devlens.domain.admin.repository;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.GetCompany;
import com.seveneleven.devlens.domain.member.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCompanyResponseConverter implements EntityConverter<GetCompany.Response, Company> {
    private final AdminProjectRepository adminProjectRepository;

    @Override
    public GetCompany.Response toDTO(Company company) {
        return new GetCompany.Response(
                company.getId(),
                company.getCompanyName(),
                company.getRepresentativeName(),
                company.getRepresentativeContact(),
                company.getRepresentativeEmail(),
                company.getAddress(),
                company.getBusinessType(),
                company.getBusinessRegistrationNumber(),
                company.getRepresentativeImageExists(),
                company.getIsActive(),
                null
        );
    }

    @Override
    public Company toEntity(GetCompany.Response response) {
        return null;
    }
}
