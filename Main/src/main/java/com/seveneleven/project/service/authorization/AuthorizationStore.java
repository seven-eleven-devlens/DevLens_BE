package com.seveneleven.project.service.authorization;

import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.PostProjectAuthorization;

import java.util.List;

public interface AuthorizationStore {
    PostProjectAuthorization.Response store(PostProjectAuthorization.Request requestDto,
                                            List<ProjectAuthorization> authorizationList,
                                            ProjectStep step);
}
