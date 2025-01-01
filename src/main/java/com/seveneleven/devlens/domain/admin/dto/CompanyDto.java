package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.domain.member.entity.Company;
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

    public Company createCompanyEntity(){
        return Company.builder()
                .companyName(companyName)
                .representativeName(representativeName)
                .representativeEmail(representativeEmail)
                .representativeContact(representativeContact)
                .address(address)
                .businessType(businessType)
                .businessRegistrationNumber(businessRegistrationNumber)
                .representativeImageExists(false)
                .activeStatus(true)
                .build();
    }
}
