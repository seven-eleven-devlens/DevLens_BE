package com.seveneleven.project.service;

import com.seveneleven.project.dto.GetProjectStep;

public interface ProjectStepService {
    GetProjectStep.Response getProjectStep(Long projectId);
}
