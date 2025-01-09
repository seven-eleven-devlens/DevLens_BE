package com.seveneleven.devlens.domain.project.repository;

import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.entity.CheckRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckRequestRepository extends JpaRepository<CheckRequest, Long> {

    @Query("""
    SELECT
        c_r.id,
        p_s.stepName,
        c.title,
        c_r.title,
        c_r.requester.name,
        c_r.requestDate
    FROM CheckRequest c_r
    JOIN c_r.checklist c
    JOIN c.projectStep p_s
    WHERE p_s.project.id = :projectId
    """)
    List<GetProjectDetail.ChecklistApplicationList> findAllApplicationLists(Long projectId);
}
