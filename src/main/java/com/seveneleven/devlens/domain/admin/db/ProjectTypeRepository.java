package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.project.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {
}
