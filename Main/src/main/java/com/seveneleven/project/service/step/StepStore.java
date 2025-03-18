package com.seveneleven.project.service.step;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.PostProjectStep;
import com.seveneleven.project.dto.PutProjectStep;

public interface StepStore {

    PostProjectStep.Response store(
            PostProjectStep.Request requestDto,
            Project project,
            Integer order
    );

    PutProjectStep.Response edit(
            PutProjectStep.Request requestDto,
            ProjectStep projectStep
    );

    ProjectStep editPosition(
            ProjectStep projectStep,
            Integer order
    );

    void delete(
            ProjectStep projectStep
    );

}
