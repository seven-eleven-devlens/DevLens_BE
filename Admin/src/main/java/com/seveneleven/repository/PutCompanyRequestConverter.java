package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.PutCompany;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PutCompanyRequestConverter implements EntityConverter<PutCompany.Request, Company> {
    /*
        함수명 : toDto
        함수 목적 : entity 를 request 로 변환
     */
    @Override
    public PutCompany.Request toDTO(Company company) {
        return null;
    }

    /*
        함수명 : toEntity
        함수 목적 : request 를 entity 로 변환
     */
    @Override
    public Company toEntity(
            PutCompany.Request request
    ) {
        return Company.createCompany(
                request.getCompanyName(),
                request.getRepresentativeName(),
                request.getRepresentativeContact(),
                request.getRepresentativeEmail(),
                request.getAddress(),
                request.getBusinessType(),
                request.getBusinessRegistrationNumber()
        );
    }
}
