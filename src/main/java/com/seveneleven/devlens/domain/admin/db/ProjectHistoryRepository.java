package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.project.entity.ProjectHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectHistoryRepository extends JpaRepository<ProjectHistory, Long> {
}
