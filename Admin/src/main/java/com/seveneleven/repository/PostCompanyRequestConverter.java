package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.PostCompany;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCompanyRequestConverter implements EntityConverter<PostCompany.Request,Company> {
    /*
        함수명 : toDto
        함수 목적 : entity 를 request 로 변환
     */
    @Override
    public PostCompany.Request toDTO(Company company) {
        //미사용 메서드
        return null;
    }

    /*
        함수명 : toEntity
        함수 목적 : request 를 entity 로 변환
     */
    @Override
    public Company toEntity(
            PostCompany.Request companyRequest
    ) {
        return Company.createCompany(
                companyRequest.getCompanyName(),
                companyRequest.getRepresentativeName(),
                companyRequest.getRepresentativeContact(),
                companyRequest.getRepresentativeEmail(),
                companyRequest.getAddress(),
                companyRequest.getBusinessType(),
                companyRequest.getBusinessRegistrationNumber()
        );
    }

}
