package com.seveneleven.project.repository;

import com.seveneleven.entity.project.CheckResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckResultRepository extends JpaRepository<CheckResult, Long> {
    Optional<CheckResult> findByCheckRequestId(Long checkRequestId);

}
