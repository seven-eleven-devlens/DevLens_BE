package com.seveneleven.devlens.domain.project.service.step;

import com.seveneleven.devlens.domain.project.dto.GetProjectStep;
import com.seveneleven.devlens.domain.project.repository.ProjectStepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StepReaderImpl implements StepReader {

    private final ProjectStepRepository projectStepRepository;

    @Override
    public GetProjectStep.Response getProjectStep(Long projectId) {
        List<GetProjectStep.ProjectStepInfo> stepInfos = projectStepRepository.findProjectStepInfo(projectId);

        for(GetProjectStep.ProjectStepInfo stepInfo : stepInfos) {
            stepInfo.setProjectChecklist(projectStepRepository.findProjectStepChecklist(stepInfo.getStepId()));
        }

        return new GetProjectStep.Response(projectId, stepInfos);
    }
}
