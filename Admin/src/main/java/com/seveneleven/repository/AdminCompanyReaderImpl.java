package com.seveneleven.repository;

import com.seveneleven.entity.member.Company;
import com.seveneleven.exception.CompanyNotFoundException;
import com.seveneleven.service.AdminCompanyReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCompanyReaderImpl implements AdminCompanyReader {
    private final CompanyRepository companyRepository;

    @Override
    public Company getCompany(Long id) {
        return companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }
}
