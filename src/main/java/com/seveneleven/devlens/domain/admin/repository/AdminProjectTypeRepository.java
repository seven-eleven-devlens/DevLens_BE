package com.seveneleven.devlens.domain.admin.repository;

import com.seveneleven.devlens.domain.project.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProjectTypeRepository extends JpaRepository<ProjectType, Long> {
}
