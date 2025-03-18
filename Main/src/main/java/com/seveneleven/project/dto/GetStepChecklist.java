package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.constant.ChecklistStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class GetStepChecklist {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long stepId;
        private List<ProjectChecklist> checklists;

        @Override
        public String toString() {
            return "Response{" +
                    "stepId=" + stepId +
                    '}';
        }

        private Response(Long stepId, List<Checklist> checklists) {
            this.stepId = stepId;
            this.checklists = checklists.stream().map(ProjectChecklist::toDto).toList();
        }

        public static Response toDto(Long stepId, List<Checklist> checklists) {
            return new Response(stepId, checklists);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProjectChecklist {
        private Long checklistId;
        private String checklistName;
        private Long checklistOrder;
        private ChecklistStatus checklistStatus;
        private LocalDateTime checkAcceptTime;

        @Override
        public String toString() {
            return "projectChecklist{" +
                    "checklistId=" + checklistId +
                    '}';
        }

        private ProjectChecklist(Checklist checklist) {
            this.checklistId = checklist.getId();
            this.checklistName = checklist.getTitle();
            this.checklistOrder = checklist.getChecklistOrder();
            this.checklistStatus = checklist.getChecklistStatus();
            this.checkAcceptTime = checklist.getApprovalDate();
        }

        public static ProjectChecklist toDto(Checklist checklist) {
            return new ProjectChecklist(checklist);
        }
    }
}
