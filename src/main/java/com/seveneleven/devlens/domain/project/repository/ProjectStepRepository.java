package com.seveneleven.devlens.domain.project.repository;

import com.seveneleven.devlens.domain.project.dto.GetProjectStep;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStepRepository extends JpaRepository<ProjectStep, Long> {

    // TODO - 한번의 쿼리문으로 합칠 수 있을까?
    @Query("""
    SELECT
        p_s.Id,
        p_s.stepName,
        null
    FROM
        ProjectStep p_s
    WHERE
        p_s.projectId.id = :projectId
    """)
    List<GetProjectStep.ProjectStepInfo> findProjectStepInfo(@Param("projectId") Long projectId);

    @Query("""
    SELECT
        p_c.id,
        p_c.title,
        p_c.isMandatory,
        p_c.approvalDate
    FROM
        Checklist p_c
    WHERE
        p_c.projectStep.Id = :stepId
    """)
    List<GetProjectStep.ProjectChecklist> findProjectStepChecklist(@Param("stepId") Long stepId);
}
