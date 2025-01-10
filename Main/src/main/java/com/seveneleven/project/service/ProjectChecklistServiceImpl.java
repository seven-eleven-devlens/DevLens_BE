package com.seveneleven.project.service;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.checklist.CheckRequestStore;
import com.seveneleven.project.service.checklist.ChecklistReader;
import com.seveneleven.project.service.checklist.ChecklistStore;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectChecklistServiceImpl implements ProjectChecklistService {
    private final ChecklistReader checklistReader;
    private final ChecklistStore checklistStore;
    private final CheckRequestStore checkRequestStore;

    @Override
    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return checklistReader.getStepChecklist(stepId);
    }

    @Override
    public PostProjectChecklist.Response postProjectChecklist(PostProjectChecklist.Request postProjectChecklist) {
        return PostProjectChecklist.Response.toDto(checklistStore.storeChecklist(postProjectChecklist));
    }

    @Override
    public PostProjectChecklistApplication.Response postProjectChecklistApplication(
            PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    ) {
        CheckRequest checkRequest = checkRequestStore.checkRequestStore(requestDto, request);
        checkRequestStore.checkRequestHistoryStore(checkRequest);
        return PostProjectChecklistApplication.Response.toDto(checkRequest);
    }

    @Override
    public PutProjectChecklist.Response putProjectChecklist(PutProjectChecklist.Request putProjectChecklist) {
        return PutProjectChecklist.Response.toDto(checklistStore.updateChecklist(putProjectChecklist));
    }

    @Override
    public DeleteProjectChecklist.Response deleteProjectChecklist(Long checklistId) {
        Checklist checklist = checklistReader.getChecklist(checklistId).deleteChecklist();
        return new DeleteProjectChecklist.Response(checklistId, checklist.getIsActive());
    }
}
