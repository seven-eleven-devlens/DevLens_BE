package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.project.dto.GetProjectStep;

public interface ProjectStepService {
    GetProjectStep.Response getProjectStep(Long projectId);
}
