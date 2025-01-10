package com.seveneleven.devlens.domain.project.service.checklist;

import com.seveneleven.devlens.domain.project.dto.PostProjectChecklist;
import com.seveneleven.devlens.domain.project.dto.PutProjectChecklist;
import com.seveneleven.devlens.domain.project.entity.Checklist;
import com.seveneleven.devlens.domain.project.repository.ChecklistRepository;
import com.seveneleven.devlens.domain.project.repository.ProjectStepRepository;
import com.seveneleven.devlens.global.entity.YesNo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChecklistStoreImpl implements ChecklistStore {

    private final ChecklistRepository checklistRepository;
    private final ProjectStepRepository projectStepRepository;

    @Override
    public Checklist storeChecklist(PostProjectChecklist.Request request) {
        return checklistRepository.save(request.toEntity(
                projectStepRepository.findByIdAndIsActive(
                        request.getProjectStepId(),
                        YesNo.YES
                ).orElseThrow(EntityNotFoundException::new)));
    }
}
