package com.seveneleven.company.service;

import com.seveneleven.entity.member.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCompanyHistoryServiceImpl implements AdminCompanyHistoryService{
    private final AdminCompanyHistoryStore adminCompanyHistoryStore;
    @Override
    public void saveHistory(Company company) {
        adminCompanyHistoryStore.store(company);
    }
}
