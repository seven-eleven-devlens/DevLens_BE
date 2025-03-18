package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.project.dto.GetStepChecklist;

import java.util.List;

public interface ChecklistReader {
    Checklist getChecklist(Long checklistId);
    List<Checklist> read(Long stepId);
}
