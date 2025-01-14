package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.ProjectAuthorizationService;
import com.seveneleven.project.service.ProjectStepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectStepFacade {

    private final ProjectStepService projectStepService;
    private final ProjectAuthorizationService projectAuthorizationService;

    public GetProjectStep.Response getProjectStepAndChecklist(Long projectId) {
        return projectStepService.getProjectStep(projectId);
    }

    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return projectStepService.getStepChecklist(stepId);
    }

    public PostProjectStep.Response postProjectStep(PostProjectStep.Request requestDto) {
        return projectStepService.postProjectStep(requestDto);
    }

    public PutProjectStep.Response putProjectStep(PutProjectStep.Request requestDto) {
        return projectStepService.putProjectStep(requestDto);
    }

    public DeleteProjectStep.Response deleteProjectStep(DeleteProjectStep.Request request) {
        return projectStepService.deleteProjectStep(request.getProjectId(), request.getStepId());
    }

    public PostProjectAuthorization.Response postProjectAuthorization(PostProjectAuthorization.Request requestDto,
                                                                      Long stepId) {
        return projectAuthorizationService.createProjectAuthorization(stepId, requestDto);
    }

    public GetProjectAuthorization.Response getProjectAuthorization(Long stepId) {
        return projectAuthorizationService.getProjectAuthorization(stepId);
    }
}
