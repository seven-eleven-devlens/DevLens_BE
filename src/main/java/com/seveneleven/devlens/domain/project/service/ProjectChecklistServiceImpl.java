package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.project.dto.GetStepChecklist;
import com.seveneleven.devlens.domain.project.dto.PostProjectChecklist;
import com.seveneleven.devlens.domain.project.service.checklist.ChecklistReader;
import com.seveneleven.devlens.domain.project.service.checklist.ChecklistStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectChecklistServiceImpl implements ProjectChecklistService {
    private final ChecklistReader checklistReader;
    private final ChecklistStore checklistStore;

    @Override
    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return checklistReader.getStepChecklist(stepId);
    }

    @Override
    public PostProjectChecklist.Response postProjectChecklist(PostProjectChecklist.Request postProjectChecklist) {
        return PostProjectChecklist.Response.toDto(checklistStore.storeChecklist(postProjectChecklist));
    }
}
