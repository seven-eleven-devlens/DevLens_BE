package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.domain.member.constant.BusinessType;
import com.seveneleven.devlens.domain.member.constant.YN;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCompany {
    @Getter
    public static class Request {
        private Long id;
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;
        private YN representativeImageExists;
        private YN isActive;

        public Request(
                Long id,
                String companyName,
                String representativeName,
                String representativeContact,
                String representativeEmail,
                String address,
                BusinessType businessType,
                String businessRegistrationNumber,
                YN representativeImageExists,
                YN activeStatus
        ) {
            this.id = id;
            this.companyName = companyName;
            this.representativeName = representativeName;
            this.representativeContact = representativeContact;
            this.representativeEmail = representativeEmail;
            this.address = address;
            this.businessType = businessType;
            this.businessRegistrationNumber = businessRegistrationNumber;
            this.representativeImageExists = representativeImageExists;
            this.isActive = activeStatus;
        }
    }

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
        private YN representativeImageExists;
        private YN isActive;
        private PaginatedResponse<GetProject.Response> projects;

        public Response(
                Long id,
                String companyName,
                String representativeName,
                String representativeContact,
                String representativeEmail,
                String address,
                BusinessType businessType,
                String businessRegistrationNumber,
                YN representativeImageExists,
                YN activeStatus,
                PaginatedResponse<GetProject.Response> projects
        ) {
            this.id = id;
            this.companyName = companyName;
            this.representativeName = representativeName;
            this.representativeContact = representativeContact;
            this.representativeEmail = representativeEmail;
            this.address = address;
            this.businessType = businessType;
            this.businessRegistrationNumber = businessRegistrationNumber;
            this.representativeImageExists = representativeImageExists;
            this.isActive = activeStatus;
            this.projects = projects;
        }

        public Response addProjectList(
                Response response,
                PaginatedResponse<GetProject.Response> projects
        ) {
            return new Response(
                    response.getId(),
                    response.getCompanyName(),
                    response.getRepresentativeName(),
                    response.getRepresentativeContact(),
                    response.getRepresentativeEmail(),
                    response.getAddress(),
                    response.getBusinessType(),
                    response.getBusinessRegistrationNumber(),
                    response.getRepresentativeImageExists(),
                    response.getIsActive(),
                    projects
            );
        }
    }
}
