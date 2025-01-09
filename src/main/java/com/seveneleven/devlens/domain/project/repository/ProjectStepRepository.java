package com.seveneleven.devlens.domain.project.repository;

import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.dto.GetProjectStep;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import com.seveneleven.devlens.global.entity.YesNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectStepRepository extends JpaRepository<ProjectStep, Long> {

    Optional<ProjectStep> findByIdAndIsActive(Long id, YesNo isActive);

    // TODO - 한번의 쿼리문으로 합칠 수 있을까?
    @Query("""
    SELECT
        p_s.id,
        p_s.stepName,
        null
    FROM
        ProjectStep p_s
    WHERE
        p_s.project.id = :projectId
    """)
    List<GetProjectStep.ProjectStepInfo> findProjectStepInfo(@Param("projectId") Long projectId);
    @Query("SELECT new com.seveneleven.devlens.domain.project.dto.GetProjectDetail$ProjectStepInfo(p_s.id, p_s.stepName, COUNT(CASE WHEN c.isChecked = 'Y' THEN 1 END) * 100.0 / COUNT(*)) " +
            "FROM ProjectStep p_s " +
            "JOIN Checklist c ON p_s.id = c.projectStep.id " +
            "WHERE p_s.project.id = :projectId " +
            "GROUP BY p_s.id")
    List<GetProjectDetail.ProjectStepInfo> findStepProcessRate(@Param("projectId") Long projectId);

    @Query("""
    SELECT
        p_c.id,
        p_c.title,
        p_c.isChecked,
        p_c.approvalDate
    FROM
        Checklist p_c
    WHERE
        p_c.projectStep.id = :stepId
    """)
    List<GetProjectStep.ProjectChecklist> findProjectStepChecklist(@Param("stepId") Long stepId);
}
