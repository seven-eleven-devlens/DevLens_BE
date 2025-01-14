package com.seveneleven.project.service.authorization;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.repository.ProjectAuthorizationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationReaderImpl implements AuthorizationReader {

    private final ProjectAuthorizationRepository projectAuthorizationRepository;

    @Override
    public List<ProjectAuthorization> readByStepId(Long projectStepId) {
        return projectAuthorizationRepository.findByProjectStepIdAndIsActiveOrderById(projectStepId, YesNo.YES);
    }

    @Override
    public List<ProjectAuthorization> readByUserId(Long memberId) {
        return projectAuthorizationRepository.findByMemberIdAndIsActiveOrderById(memberId, YesNo.YES);
    }

    @Override
    public ProjectAuthorization readByStepIdAndUserId(Long projectStepId, Long userId) {
        return projectAuthorizationRepository.findByProjectStepIdAndMemberIdAndIsActive(projectStepId, userId, YesNo.YES)
                .orElseThrow(EntityNotFoundException::new);
    }
}
