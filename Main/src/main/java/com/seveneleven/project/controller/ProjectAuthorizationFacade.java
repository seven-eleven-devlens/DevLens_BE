package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetProjectAuthorization;
import com.seveneleven.project.dto.PostProjectAuthorization;
import com.seveneleven.project.service.ProjectAuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectAuthorizationFacade {

    private final ProjectAuthorizationService projectAuthorizationService;

    /**
     * 함수명 : postProjectAuthorization
     * 프로젝트 접근 권한을 편힙하는 함수
     */
    public PostProjectAuthorization.Response postProjectAuthorization(
            PostProjectAuthorization.Request requestDto,
            Long stepId
    ) {
        return projectAuthorizationService.createProjectAuthorization(stepId, requestDto);
    }

    /**
     * 함수명 : getProjectAuthorization
     * 해당 단계에 접근할 수 있는 인원을 반환하는 함수
     */
    public GetProjectAuthorization.Response getProjectAuthorization(Long stepId) {
        return projectAuthorizationService.getProjectAuthorization(stepId);
    }
}
