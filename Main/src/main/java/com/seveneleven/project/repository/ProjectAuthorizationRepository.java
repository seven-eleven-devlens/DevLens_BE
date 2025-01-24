package com.seveneleven.project.repository;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectAuthorizationRepository extends JpaRepository<ProjectAuthorization, Long> {
    List<ProjectAuthorization> findByProjectStepIdAndIsActiveOrderById(Long projectStepId, YesNo isActive);
    List<ProjectAuthorization> findByMemberIdAndIsActiveOrderById(Long memberId, YesNo isActive);
    Optional<ProjectAuthorization> findByProjectStepIdAndMemberIdAndIsActive(Long projectStepId, Long memberId, YesNo yesno);
}
