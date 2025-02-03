package com.seveneleven.service;

import com.seveneleven.common.BasicStep;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProjectStepServiceImpl implements AdminProjectStepService {

    private final ProjectStepStore projectStepStore;

    @Override
    @Transactional
    public void createBasicStep(Project project) {
        List<ProjectStep> basicSteps = BasicStep.createSteps(project);

        for(ProjectStep step : basicSteps) {
            projectStepStore.store(step);
        }
    }
}
