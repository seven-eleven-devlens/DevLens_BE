package com.seveneleven.devlens.domain.member.repository;

import com.seveneleven.devlens.domain.member.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findById(Long id);
}
