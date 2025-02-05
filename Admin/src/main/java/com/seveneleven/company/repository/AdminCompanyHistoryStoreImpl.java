package com.seveneleven.company.repository;

import com.seveneleven.company.service.AdminCompanyHistoryStore;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.CompanyHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCompanyHistoryStoreImpl implements AdminCompanyHistoryStore {
    private final AdminCompanyHistoryRepository adminCompanyHistoryRepository;

    @Override
    public void store(Company company) {
        adminCompanyHistoryRepository.save(CompanyHistory.of(company));
    }
}
