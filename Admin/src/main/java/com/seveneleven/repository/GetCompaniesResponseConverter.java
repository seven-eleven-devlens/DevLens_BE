package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.GetCompanies;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCompaniesResponseConverter implements EntityConverter<GetCompanies.Response, Company> {
    @Override
    public GetCompanies.Response toDTO(Company company) {
        return GetCompanies.Response.getCompaniesResponse(company);
    }

    @Override
    public Company toEntity(GetCompanies.Response response) {
        return null;
    }
}
