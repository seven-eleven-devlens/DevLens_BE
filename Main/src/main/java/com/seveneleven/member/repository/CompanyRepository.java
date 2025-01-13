package com.seveneleven.member.repository;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.YN;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByIdAndIsActive(Long id, YN isActive);

    Page<Company> findByIsActive(Pageable pageable, YN isActive);
}
