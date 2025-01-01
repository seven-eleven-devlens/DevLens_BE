package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.member.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    public Company findByBusinessRegistrationNumber(String businessRegistrationNumber);
}