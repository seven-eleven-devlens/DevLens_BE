package com.seveneleven.project.service.authorization;

import com.seveneleven.entity.project.ProjectAuthorization;

import java.util.List;

public interface AuthorizationReader {
    List<ProjectAuthorization> readByStepId(Long projectStepId);
    List<ProjectAuthorization> readByUserId(Long userId);
    ProjectAuthorization readByStepIdAndUserId(Long projectStepId, Long userId);
}
