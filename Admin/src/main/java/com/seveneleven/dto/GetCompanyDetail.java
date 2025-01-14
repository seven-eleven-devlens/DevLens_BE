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
                Company company
        ) {
            this.id = company.getId();
            this.companyName = company.getCompanyName();
            this.representativeName = company.getRepresentativeName();
            this.representativeContact = company.getRepresentativeContact();
            this.representativeEmail = company.getRepresentativeEmail();
            this.address = company.getAddress();
            this.businessType = company.getBusinessType();
            this.businessRegistrationNumber = company.getBusinessRegistrationNumber();
        }

        private Response(
                Response response
        ) {
            this.id = response.getId();
            this.companyName = response.getCompanyName();
            this.representativeName = response.getRepresentativeName();
            this.representativeContact = response.getRepresentativeContact();
            this.representativeEmail = response.getRepresentativeEmail();
            this.address = response.getAddress();
            this.businessType = response.getBusinessType();
            this.businessRegistrationNumber = response.getBusinessRegistrationNumber();
        }

        public static Response getCompanyResponse(
                Company company
        ) {
            return new Response(
                    company
            );
        }

        public static Response addProjectList(
                Response response,
                PaginatedResponse<GetCompanyProject> projects
        ) {
            Response newResponse = new Response(response);
            newResponse.projects = projects;
            return newResponse;
        }
    }
}
