package com.seveneleven.project.service.project;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectStoreImpl implements ProjectStore {

    @Override
    public Project modifyProjectCurrentStep(Project project, ProjectStep step) {
        project.modifyCurrentProjectStep(step.getStepName());
        return project;
    }
}
