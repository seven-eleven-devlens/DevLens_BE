package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.project.dto.GetStepChecklist;

public interface ChecklistReader {
    Checklist getChecklist(Long checklistId);
    GetStepChecklist.Response getStepChecklist(Long stepId);
}
