package com.seveneleven.repository;

import com.seveneleven.entity.project.ProjectHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProjectHistoryRepository extends JpaRepository<ProjectHistory, Long> {
}
