package com.seveneleven.devlens.domain.project.service.step;


import com.seveneleven.devlens.domain.project.dto.GetProjectStep;

public interface StepReader {

    GetProjectStep.Response getProjectStep(Long projectStep);
}
