package com.seveneleven.company.repository;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.YN;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByBusinessRegistrationNumberAndIsActive(String businessRegistrationNumber, YN isActive);

    Optional<Company> findByIdAndIsActive(Long id, YN isActive);

    Page<Company> findByCompanyNameContainingIgnoreCase(String searchTerm, Pageable pageable);
    List<Company> findAllByIsActiveOrderByCompanyNameAsc(YN isActive);
}