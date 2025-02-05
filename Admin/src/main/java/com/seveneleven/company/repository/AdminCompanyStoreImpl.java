package com.seveneleven.company.repository;

import com.seveneleven.company.service.AdminCompanyStore;
import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCompanyStoreImpl implements AdminCompanyStore {
    private final CompanyRepository companyRepository;
    @Override
    public Company store(Company company) {
        return companyRepository.save(company);
    }
}
