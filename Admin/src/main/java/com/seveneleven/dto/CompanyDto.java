package com.seveneleven.dto;

import com.seveneleven.entity.member.constant.BusinessType;
import com.seveneleven.entity.member.constant.YN;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyDto {
    @Getter
    public static class CompanyRequest {
        private Long id;
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;
        private YN isActive;

        public CompanyRequest(
                Long id,
                String companyName,
                String representativeName,
                String representativeContact,
                String representativeEmail,
                String address,
                BusinessType businessType,
                String businessRegistrationNumber,
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
            this.isActive = activeStatus;
        }
    }

    @Getter
    public static class CompanyResponse {
        private Long id;
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;
        private YN isActive;

        public CompanyResponse(
                Long id,
                String companyName,
                String representativeName,
                String representativeContact,
                String representativeEmail,
                String address,
                BusinessType businessType,
                String businessRegistrationNumber,
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
            this.isActive = activeStatus;
        }
    }
}
