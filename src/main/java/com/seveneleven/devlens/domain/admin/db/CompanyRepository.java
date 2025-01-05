package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.member.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByBusinessRegistrationNumber(String businessRegistrationNumber);
    @Query("SELECT c FROM Company c")
    Page<Company> findPage(Pageable pageable);
}