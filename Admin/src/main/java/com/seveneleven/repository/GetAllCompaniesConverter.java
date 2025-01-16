package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.GetAllCompanies;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllCompaniesConverter implements EntityConverter<GetAllCompanies, Company> {

    @Override
    public GetAllCompanies toDTO(Company company) {
        return GetAllCompanies.of(company);
    }

    @Override
    public Company toEntity(GetAllCompanies getAllCompanies) {
        return null;
    }
}
