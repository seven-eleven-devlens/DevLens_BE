package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByBusinessRegistrationNumberAndIsActive(String businessRegistrationNumber, YN isActive);
    Optional<Company> findByIdAndIsActive(Long id, YN isActive);
    Page<Company> findByIsActive(Pageable pageable, YN isActive);
    @Transactional
    @Modifying
    @Query("UPDATE Company c SET c.isActive = 'N' WHERE c.id = :id")
    void deactivateCompany(@Param("id") Long id);
}