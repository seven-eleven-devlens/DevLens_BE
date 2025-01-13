package com.seveneleven.service;

import com.seveneleven.common.CheckCompanyValidity;
import com.seveneleven.dto.PostCompany;
import com.seveneleven.repository.CompanyRepository;
import com.seveneleven.repository.PostCompanyRequestConverter;
import com.seveneleven.repository.PostCompanyResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCreateService {
    private final CompanyRepository companyRepository;
    private final PostCompanyRequestConverter postCompanyRequestConverter;
    private final PostCompanyResponseConverter postCompanyResponseConverter;
    private final CheckCompanyValidity checkCompanyValidity;

    /*
        함수명 : createCompany
        함수 목적 : 함수 정보 저장
     */
    public PostCompany.Response createCompany(
            PostCompany.Request companyRequest
    ) {
        //사업자 등록번호 중복 조회
        checkCompanyValidity.checkDuplicatedCompanyBusinessRegistrationNumber(companyRequest.getBusinessRegistrationNumber());
        return postCompanyResponseConverter.toDTO(companyRepository.save(postCompanyRequestConverter.toEntity(companyRequest)));
    }
}
