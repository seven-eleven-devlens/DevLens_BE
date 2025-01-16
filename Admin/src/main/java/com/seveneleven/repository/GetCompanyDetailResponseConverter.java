package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.GetCompanyDetail;
import com.seveneleven.dto.GetCompanyProject;
import com.seveneleven.dto.PaginatedResponse;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCompanyDetailResponseConverter implements EntityConverter<GetCompanyDetail.Response, Company> {

    public GetCompanyDetail.Response toDTO(Company company, PaginatedResponse<GetCompanyProject> paginatedResponse) {
        return GetCompanyDetail.Response.of(company, paginatedResponse);
    }


    @Override
    public GetCompanyDetail.Response toDTO(Company company) {
        return GetCompanyDetail.Response.of(company, null);
    }

    @Override
    public Company toEntity(GetCompanyDetail.Response response) {
        return null;
    }
}
