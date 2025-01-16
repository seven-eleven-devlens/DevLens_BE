package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import lombok.Getter;

@Getter
public class GetAllCompanies {
    private Long id;
    private String companyName;
    private GetAllCompanies(Company company) {
        this.id = company.getId();
        this.companyName = company.getCompanyName();
    }

    public static GetAllCompanies of(Company company) {
        return new GetAllCompanies(company);
    }
}
