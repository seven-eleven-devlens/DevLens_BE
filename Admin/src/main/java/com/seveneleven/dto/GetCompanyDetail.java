package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.BusinessType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCompanyDetail {
    @Getter
    public static class Response {
        private Long id;
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;
        private PaginatedResponse<GetCompanyProject> projects;

        private Response(
                Company company, PaginatedResponse<GetCompanyProject> projects
        ) {
            this.id = company.getId();
            this.companyName = company.getCompanyName();
            this.representativeName = company.getRepresentativeName();
            this.representativeContact = company.getRepresentativeContact();
            this.representativeEmail = company.getRepresentativeEmail();
            this.address = company.getAddress();
            this.businessType = company.getBusinessType();
            this.businessRegistrationNumber = company.getBusinessRegistrationNumber();
            this.projects = projects;
        }

        public static Response of(
                Company company,
                PaginatedResponse<GetCompanyProject> projects
        ) {
            return new Response(company, projects);
        }
    }
}
