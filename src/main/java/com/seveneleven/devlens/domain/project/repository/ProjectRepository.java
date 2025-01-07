package com.seveneleven.devlens.domain.project.repository;

import com.seveneleven.devlens.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("""
        SELECT p
        FROM Project p
        JOIN ProjectAuthorization p_a
            ON
                p_a.member.id = :memberId AND
                p_a.isDeleted = 'N' AND
                p_a.projectStep.project.id = p.id
        WHERE
            p.projectStatusCode = 'IN_PROGRESS'
    """)
    List<Project> findAllProgressingProjects(Long memberId);

    @Query("""
        SELECT p
        FROM Project p
        WHERE
            p.projectStatusCode = 'IN_PROGRESS' AND
            (
                p.customer.id = :companyId OR
                p.developer.id = :companyId
            )
    """)
    List<Project> findAllCompanyProgressingProjects(Long companyId);


}
