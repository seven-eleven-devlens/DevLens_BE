package com.seveneleven.repository;

import com.seveneleven.entity.project.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProjectTypeRepository extends JpaRepository<ProjectType, Long> {
}
