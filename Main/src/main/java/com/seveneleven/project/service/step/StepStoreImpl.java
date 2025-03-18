package com.seveneleven.project.service.step;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.PostProjectStep;
import com.seveneleven.project.dto.PutProjectStep;
import com.seveneleven.project.repository.ChecklistRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StepStoreImpl implements StepStore {

    private final ProjectStepRepository projectStepRepository;
    private final ChecklistRepository checklistRepository;

    @Override
    public PostProjectStep.Response store(
            PostProjectStep.Request requestDto,
            Project project,
            Integer order
    ) {
        ProjectStep projectStep = requestDto.toEntity(project, order);
        projectStepRepository.save(projectStep);

        int index = 1;

        for(PostProjectStep.PostChecklist postChecklist : requestDto.getChecklists()) {
            if(postChecklist.getChecklistTitle() == null || postChecklist.getChecklistTitle().isEmpty()) {
                continue;
            }

            checklistRepository.save(Checklist.create(postChecklist.getChecklistTitle(), postChecklist.getChecklistDescription(), index * 100, projectStep));
            index++;
        }

        return PostProjectStep.Response.toDto(projectStep, requestDto.getChecklists());
    }

    @Override
    public PutProjectStep.Response edit(
            PutProjectStep.Request requestDto,
            ProjectStep projectStep
    ) {
        projectStepRepository.save(projectStep.edit(requestDto.getStepName(), requestDto.getStepDescription()));

        return PutProjectStep.Response.toDto(projectStep);
    }

    @Override
    public ProjectStep editPosition(ProjectStep projectStep, Integer order) {
        return projectStepRepository.save(projectStep.editOrder(order));
    }

    @Override
    public void delete(
            ProjectStep projectStep
    ) {
        projectStep.delete();
    }
}
