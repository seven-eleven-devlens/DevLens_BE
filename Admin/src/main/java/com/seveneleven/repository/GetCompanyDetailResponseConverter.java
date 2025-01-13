package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.GetCompanyDetail;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCompanyDetailResponseConverter implements EntityConverter<GetCompanyDetail.Response, Company> {
    @Override
    public GetCompanyDetail.Response toDTO(Company company) {
        return GetCompanyDetail.Response.getCompanyResponse(
                company
        );

    }

    @Override
    public Company toEntity(GetCompanyDetail.Response response) {
        return null;
    }
}
