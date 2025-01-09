package com.seveneleven.devlens.domain.project.repository;

import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStepRepository extends JpaRepository<ProjectStep, Long> {
    // BoardService메서드 작성을 위해 임시로 생성

}
