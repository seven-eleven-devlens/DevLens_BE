package com.seveneleven.project.service.project;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;

public interface ProjectStore {

    Project modifyProjectCurrentStep(Project project, ProjectStep projectStep);
}
