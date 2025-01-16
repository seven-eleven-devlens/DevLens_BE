package com.seveneleven.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class CompanyResponse {
    private Long companyId;
    private String companyName;
    private String department;
    private String position;
}
