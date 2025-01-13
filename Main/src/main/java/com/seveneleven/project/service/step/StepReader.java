package com.seveneleven.project.service.step;

import com.seveneleven.project.dto.GetProjectStep;

public interface StepReader {

    GetProjectStep.Response getProjectStep(Long projectStep);
}
