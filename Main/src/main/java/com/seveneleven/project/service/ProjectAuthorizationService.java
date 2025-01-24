package com.seveneleven.project.service;

import com.seveneleven.project.dto.GetProjectAuthorization;
import com.seveneleven.project.dto.PostProjectAuthorization;

public interface ProjectAuthorizationService {
    GetProjectAuthorization.Response getProjectAuthorization(Long stepId);
    PostProjectAuthorization.Response createProjectAuthorization(Long stepId, PostProjectAuthorization.Request requestDto);
}
