package com.seveneleven.devlens.domain.admin.repository;

import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByBusinessRegistrationNumberAndIsActive(String businessRegistrationNumber, YN isActive);

    Optional<Company> findByIdAndIsActive(Long id, YN isActive);

    Page<Company> findByIsActive(Pageable pageable, YN isActive);
}