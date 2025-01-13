package com.seveneleven.service;

import com.seveneleven.common.CheckCompanyValidity;
import com.seveneleven.dto.CompanyDto;
import com.seveneleven.repository.CompanyRepository;
import com.seveneleven.repository.CompanyRequestConverter;
import com.seveneleven.repository.CompanyResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCreateService {
    private final CompanyRepository companyRepository;
    private final CompanyRequestConverter companyRequestConverter;
    private final CompanyResponseConverter companyResponseConverter;
    private final CheckCompanyValidity checkCompanyValidity;

    /*
        함수명 : createCompany
        함수 목적 : 함수 정보 저장
     */
    public CompanyDto.CompanyResponse createCompany(
            CompanyDto.CompanyRequest companyRequest
    ) {
        //사업자 등록번호 중복 조회
        checkCompanyValidity.checkDuplicatedCompanyBusinessRegistrationNumber(companyRequest.getBusinessRegistrationNumber());
        //회사 저장, 대표 이미지 존재 여부는 파일 등록 시 변경
        var company = companyRequestConverter.toEntity(companyRequest);
        return companyResponseConverter.toDTO(companyRepository.save(company));
    }
}
