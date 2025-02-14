package com.seveneleven.company.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.BusinessType;
import com.seveneleven.entity.member.constant.YN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PutCompany {
    @Getter
    public static class Request {
        @NotBlank
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private YN isActive;
        private String businessRegistrationNumber;

        @Override
        public String toString() {
            return "Request{" +
                    "companyName='" + companyName + '\'' +
                    '}';
        }

        public Company updateCompany(Company company) {
            return company.updateCompany(
                    companyName,
                    representativeName,
                    representativeContact,
                    representativeEmail,
                    isActive,
                    address,
                    businessType,
                    businessRegistrationNumber
            );
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
        private YN isActive;

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    '}';
        }

        private Response(Company company) {
            id = company.getId();
            companyName = company.getCompanyName();
            representativeName = company.getRepresentativeName();
            representativeContact = company.getRepresentativeContact();
            representativeEmail = company.getRepresentativeEmail();
            address = company.getAddress();
            businessType = company.getBusinessType();
            businessRegistrationNumber = company.getBusinessRegistrationNumber();
            isActive = company.getIsActive();
        }

        public static Response of(Company company) {
            return new Response(company);
        }
    }
}
