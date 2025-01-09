package com.seveneleven.devlens.domain.project.controller;

import com.seveneleven.devlens.domain.project.dto.GetProjectStep;
import com.seveneleven.devlens.domain.project.dto.GetStepChecklist;
import com.seveneleven.devlens.domain.project.dto.PostProjectChecklist;
import com.seveneleven.devlens.domain.project.service.ProjectChecklistService;
import com.seveneleven.devlens.domain.project.service.ProjectStepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectChecklistFacade {

    private final ProjectStepService projectStepService;
    private final ProjectChecklistService projectChecklistService;

    public GetProjectStep.Response getProjectStepAndChecklist(Long projectId) {
        return projectStepService.getProjectStep(projectId);
    }

    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return projectChecklistService.getStepChecklist(stepId);
    }

    public PostProjectChecklist.Response postProjectChecklist(PostProjectChecklist.Request postProjectChecklist) {
        return projectChecklistService.postProjectChecklist(postProjectChecklist);
    }
}
