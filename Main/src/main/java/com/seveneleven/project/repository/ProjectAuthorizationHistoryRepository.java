package com.seveneleven.project.repository;

import com.seveneleven.entity.project.ProjectAuthorizationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAuthorizationHistoryRepository extends JpaRepository<ProjectAuthorizationHistory, Long> {
}
