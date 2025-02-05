package com.seveneleven.project.service.step;

import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.GetProjectStep;

import java.util.List;

public interface StepReader {
    ProjectStep read(Long stepId);
    GetProjectStep.Response getProjectStep(Long projectStep);
    List<Integer> getStepOrderList(Long projectId);
}
