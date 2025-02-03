package com.seveneleven.service;

import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.repository.AdminProjectStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStepStoreImpl implements ProjectStepStore {

    private final AdminProjectStepRepository projectStepRepository;

    @Override
    public void store(ProjectStep projectStep) {
        projectStepRepository.save(projectStep);
    }
}
