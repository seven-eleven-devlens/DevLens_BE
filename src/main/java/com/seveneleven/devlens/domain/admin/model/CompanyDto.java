package com.seveneleven.devlens.domain.admin.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private String companyName;
    private String representativeName;
    private String representativeContact;
    private String representativeEmail;
    private String address;
    private String businessType;
    private String businessRegistrationNumber;
    private Boolean representativeImageExists;
    private Boolean activeStatus;
    public CompanyDto returnCompanyStatus() {
        return CompanyDto.builder()
                .companyName(this.companyName)
                .representativeName(this.representativeName)
                .representativeContact(this.representativeContact)
                .representativeEmail(this.representativeEmail)
                .address(this.address)
                .businessType(this.businessType)
                .businessRegistrationNumber(this.businessRegistrationNumber)
                .activeStatus(this.activeStatus)
                .build();
    }
}
