package com.seveneleven.devlens.domain.project.repository;

import com.seveneleven.devlens.domain.project.dto.GetStepChecklist;
import com.seveneleven.devlens.domain.project.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    @Query("""
        SELECT
            new com.seveneleven.devlens.domain.project.dto.GetStepChecklist$projectChecklist(
                    c.id,
                    c.title,
                    c.isChecked,
                    c.approvalDate
                )
        FROM Checklist c
        WHERE c.projectStep.id = :projectStepId AND c.isActive = 'Y'
        ORDER BY c.id
    """)
    List<GetStepChecklist.projectChecklist> findStepChecklist(@Param("stepId") Long stepId);
}