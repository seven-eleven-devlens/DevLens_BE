package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.GetCompany;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCompanyResponseConverter implements EntityConverter<GetCompany.Response, Company> {
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
                company.getIsActive(),
                null
        );
    }

    @Override
    public Company toEntity(GetCompany.Response response) {
        return null;
    }
}
