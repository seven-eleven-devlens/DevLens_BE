package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.project.dto.GetProjectStep;
import com.seveneleven.devlens.domain.project.service.step.StepReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectStepServiceImpl implements ProjectStepService {

    private final StepReader stepReader;

    @Override
    public GetProjectStep.Response getProjectStep(Long projectId) {
        return stepReader.getProjectStep(projectId);
    }
}
