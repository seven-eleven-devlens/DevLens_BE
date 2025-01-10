package com.seveneleven.devlens.domain.project.service.checklist;

import com.seveneleven.devlens.domain.project.dto.GetStepChecklist;
import com.seveneleven.devlens.domain.project.repository.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChecklistReaderImpl implements ChecklistReader {

    private final ChecklistRepository checklistRepository;

    @Override
    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return new GetStepChecklist.Response(stepId, checklistRepository.findStepChecklist(stepId));
    }
}
