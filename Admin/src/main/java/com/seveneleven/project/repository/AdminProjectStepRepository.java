package com.seveneleven.repository;

import com.seveneleven.entity.project.ProjectStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminProjectStepRepository extends JpaRepository<ProjectStep, Long> {
}
