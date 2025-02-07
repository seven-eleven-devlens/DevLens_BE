package com.seveneleven.company.repository;

import com.seveneleven.entity.member.CompanyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCompanyHistoryRepository extends JpaRepository<CompanyHistory, Long> {
}
