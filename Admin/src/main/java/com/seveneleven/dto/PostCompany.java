package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.BusinessType;
import lombok.Getter;

public class PostCompany {
    @Getter
    public static class Request {
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;
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

        private Response(Company company) {
            this.id = company.getId();
            this.companyName = company.getCompanyName();
            this.representativeName = company.getRepresentativeName();
            this.representativeContact = company.getRepresentativeContact();
            this.representativeEmail = company.getRepresentativeEmail();
            this.address = company.getAddress();
            this.businessType = company.getBusinessType();
            this.businessRegistrationNumber = company.getBusinessRegistrationNumber();
        }

        public static Response of (Company company) {
            return new Response(company);
        }
    }
}
