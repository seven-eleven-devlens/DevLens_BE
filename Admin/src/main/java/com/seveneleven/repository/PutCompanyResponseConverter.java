package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.PutCompany;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PutCompanyResponseConverter implements EntityConverter<PutCompany.Response, Company> {
    /*
        함수명 : toDTO
        함수 목적 : response 를 DTO 로 변환
     */
    @Override
    public PutCompany.Response toDTO(
            Company company
    ) {
        return new PutCompany.Response(
                company.getId(),
                company.getCompanyName(),
                company.getRepresentativeName(),
                company.getRepresentativeContact(),
                company.getRepresentativeEmail(),
                company.getAddress(),
                company.getBusinessType(),
                company.getBusinessRegistrationNumber(),
                company.getIsActive());
    }

    /*
        함수명 : toEntity
        함수 목적 : Dto 를 Entity 로 변환
     */
    @Override
    public Company toEntity(
            PutCompany.Response response
    ) {
        return null;
    }
}
