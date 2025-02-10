package com.seveneleven.company.dto;

import com.seveneleven.entity.member.Company;
import lombok.Getter;

@Getter
public class GetAllCompanies {
    private Long id;
    private String companyName;

    private GetAllCompanies(Company company) {
        id = company.getId();
        companyName = company.getCompanyName();

    }

    @Override
    public String toString() {
        return "GetAllCompanies{" +
                "id=" + id +
                '}';
    }

    public static GetAllCompanies of(Company company) {
        return new GetAllCompanies(company);
    }
}
