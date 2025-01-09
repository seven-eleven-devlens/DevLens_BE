package com.seveneleven.devlens.domain.project.repository;

import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStepRepository extends JpaRepository<ProjectStep, Long> {

//    @Query("SELECT p_s.id, p_s.stepName, COUNT(CASE WHEN c.isChecked = 'Y' THEN 1 END) * 100.0 / COUNT(*) AS completion_percentage " +
//            "FROM ProjectStep p_s " +
//            "JOIN Checklist c ON p_s.id = c.projectStep.id " +
//            "WHERE p_s.project.id = :projectId " +
//            "GROUP BY p_s.id")
//    List<GetProjectDetail.ProjectStepInfo> findStepProcessRate(@Param("projectId") Long projectId);

    @Query("SELECT new com.seveneleven.devlens.domain.project.dto.GetProjectDetail$ProjectStepInfo(p_s.id, p_s.stepName, COUNT(CASE WHEN c.isChecked = 'Y' THEN 1 END) * 100.0 / COUNT(*)) " +
            "FROM ProjectStep p_s " +
            "JOIN Checklist c ON p_s.id = c.projectStep.id " +
            "WHERE p_s.project.id = :projectId " +
            "GROUP BY p_s.id")
    List<GetProjectDetail.ProjectStepInfo> findStepProcessRate(@Param("projectId") Long projectId);

}
