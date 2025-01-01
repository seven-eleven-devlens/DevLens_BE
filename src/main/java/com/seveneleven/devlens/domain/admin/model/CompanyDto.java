package com.seveneleven.devlens.domain.admin.model;

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
    private Boolean representativeImageExists;
    private Boolean activeStatus;
}
