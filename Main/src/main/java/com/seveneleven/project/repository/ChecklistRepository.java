package com.seveneleven.project.repository;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.constant.ChecklistStatus;
import com.seveneleven.project.dto.GetStepChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    @Query("""
        SELECT
            c.checklistOrder
        FROM Checklist c
        WHERE c.projectStep.id = :projectStepId
            AND
              c.checklistStatus <> :checklistStatus
        order by c.checklistOrder
    """)
    List<Integer> findChecklistOrderByProjectStepIdAndChecklistStatusNot(Long projectStepId, ChecklistStatus checklistStatus);

    Optional<Checklist> findByIdAndChecklistStatusNot(@Param("id") Long checklistId, @Param("checklistStatus") ChecklistStatus checklistStatus);

    List<Checklist> findByProjectStepIdAndChecklistStatusNotOrderByChecklistOrder(@Param("projectStepId") Long projectStepId, ChecklistStatus checklistStatus);
}