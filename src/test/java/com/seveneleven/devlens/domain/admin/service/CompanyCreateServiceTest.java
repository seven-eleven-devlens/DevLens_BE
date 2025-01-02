package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.db.CompanyConverter;
import com.seveneleven.devlens.domain.admin.model.CompanyDto;
import com.seveneleven.devlens.domain.admin.exception.CompanyDuplicatedException;
import com.seveneleven.devlens.domain.admin.db.CompanyRepository;
import com.seveneleven.devlens.domain.member.entity.Company;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyCreateServiceTest {
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CompanyCreateService companyService;
    @Mock
    private CompanyConverter companyConverter;

    @DisplayName("회사 추가하기 기능")
    @Test
    void createCompany() {
        var companyName = "companyName";
        var representativeName = "representativeName";
        var representativeEmail = "representativeEmail";
        var representativeContact = "representativeContact";
        var address = "address";
        var businessType = "businessType";
        var businessRegistrationNumber = "businessRegistrationNumber";
        var representativeImageExists = false;
        var activeStatus = true;

        when(companyRepository.save(any(Company.class)))
                .thenAnswer(invocation -> {
                            var e = (Company) invocation.getArgument(0);
                            e.setCompanyName(companyName);
                            e.setRepresentativeName(representativeName);
                            e.setRepresentativeEmail(representativeEmail);
                            e.setRepresentativeContact(representativeContact);
                            e.setAddress(address);
                            e.setBusinessType(businessType);
                            e.setBusinessRegistrationNumber(businessRegistrationNumber);
                            e.setRepresentativeImageExists(representativeImageExists);
                            e.setActiveStatus(activeStatus);
                            return e;
                        }
                );
        CompanyDto companyDto = new CompanyDto(companyName, representativeName, representativeEmail, representativeContact, address, businessType, businessRegistrationNumber, true, true);

        var actual = companyService.createCompany(companyDto);
        verify(companyRepository, times(1)).save(any(Company.class));
        assertEquals(companyName, actual.getCompanyName());
        assertEquals(representativeName, actual.getRepresentativeName());
        assertEquals(representativeEmail, actual.getRepresentativeEmail());
        assertEquals(representativeContact, actual.getRepresentativeContact());
        assertEquals(address, actual.getAddress());
        assertEquals(businessType, actual.getBusinessType());
        assertEquals(businessRegistrationNumber, actual.getBusinessRegistrationNumber());
        assertEquals(representativeImageExists, actual.getRepresentativeImageExists());
        assertEquals(activeStatus, actual.getActiveStatus());
    }

    @DisplayName("중복 시 예외처리 발생 확인")
    @Test
    void createCompany_shouldThrowException() {
        var companyName = "companyName";
        var representativeName = "representativeName";
        var representativeEmail = "representativeEmail";
        var representativeContact = "representativeContact";
        var address = "address";
        var businessType = "businessType";
        // Given
        String duplicatedBusinessNumber = "1234567890";
        CompanyDto companyDto = new CompanyDto(companyName, representativeName, representativeContact, representativeEmail, address, businessType, duplicatedBusinessNumber, true, true);
        Company company = companyConverter.toEntity(companyDto);

        // Mock: 이미 등록된 회사가 있는 경우를 시뮬레이션
        when(companyRepository.findByBusinessRegistrationNumber(duplicatedBusinessNumber))
                .thenReturn(company);

        // When & Then
        assertThrows(CompanyDuplicatedException.class,
                () -> companyService.createCompany(companyDto));

        // Verify: repository 호출이 제대로 이루어졌는지 확인
        verify(companyRepository, times(1)).findByBusinessRegistrationNumber(duplicatedBusinessNumber);
        verify(companyRepository, never()).save(any(Company.class)); // save는 호출되지 않아야 함
    }
}