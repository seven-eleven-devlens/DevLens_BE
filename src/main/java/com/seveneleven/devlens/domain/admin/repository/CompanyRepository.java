package com.seveneleven.devlens.domain.admin.repository;

import com.seveneleven.devlens.domain.member.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    public Company findByBusinessRegistrationNumber(String businessRegistrationNumber);
}