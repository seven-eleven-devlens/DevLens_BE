package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.constant.ChecklistStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PutProjectChecklistPosition {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long checklistId;
        private String checklistName;
        private Integer checklistOrder;
        private ChecklistStatus checklistStatus;
        private LocalDateTime checkAcceptTime;

        @Override
        public String toString() {
            return "PutProjectChecklistPosition{" +
                    "checklistId=" + checklistId +
                    '}';
        }

        private Response(Checklist checklist) {
            this.checklistId = checklist.getId();
            this.checklistName = checklist.getTitle();
            this.checklistOrder = checklist.getChecklistOrder();
            this.checklistStatus = checklist.getChecklistStatus();
            this.checkAcceptTime = checklist.getApprovalDate();
        }

        public static PutProjectChecklistPosition.Response toDto(Checklist checklist) {
            return new Response(checklist);
        }
    }
}
