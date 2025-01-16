package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.PostCompany;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCompanyResponseConverter implements EntityConverter<PostCompany.Response, Company> {

    @Override
    public PostCompany.Response toDTO(Company company) {
            return PostCompany.Response.of(company);
    }

    @Override
    public Company toEntity(PostCompany.Response response) {
        return null;
    }
}