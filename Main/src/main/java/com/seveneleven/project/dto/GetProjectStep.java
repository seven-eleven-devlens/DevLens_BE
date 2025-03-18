package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetProjectStep {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectId;
        private List<ProjectStepInfo> projectStepInfo = new ArrayList<>();

        @Override
        public String toString() {
            return "Response{" +
                    "projectId=" + projectId +
                    '}';
        }

        private Response(Long projectId) {
            this.projectId = projectId;
            this.projectStepInfo = new ArrayList<>();
        }

        public static Response create(Long projectId) {
            return new Response(projectId);
        }

        public void add(ProjectStep projectStep, List<Checklist> checklists) {
            projectStepInfo.add(ProjectStepInfo.toDto(projectStep, projectStepInfo.size() + 1, checklists));
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProjectStepInfo {
        private Long stepId;
        private String stepName;
        private Integer stepOrder;
        private List<ProjectChecklist> projectChecklist;

        @Override
        public String toString() {
            return "ProjectStepInfo{" +
                    "stepId=" + stepId +
                    '}';
        }

        private ProjectStepInfo(ProjectStep projectStep, Integer order, List<Checklist> projectChecklist) {
            stepId = projectStep.getId();
            stepName = projectStep.getStepName();
            stepOrder = order;
            this.projectChecklist = projectChecklist.stream().map(ProjectChecklist::new).toList();
        }

        public static ProjectStepInfo toDto(ProjectStep projectStep, Integer order, List<Checklist> projectChecklist) {
            return new ProjectStepInfo(projectStep, order, projectChecklist);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProjectChecklist {
        private Long checklistId;
        private String checklistName;
        private String checklistStatus;
        private Long checklistOrder;
        private LocalDateTime approvalTime;

        @Override
        public String toString() {
            return "ProjectChecklist{" +
                    "checklistId=" + checklistId +
                    '}';
        }

        public ProjectChecklist(Checklist checklist) {
            this.checklistId = checklist.getId();
            this.checklistName = checklist.getTitle();
            this.checklistStatus = checklist.getChecklistStatus().name();
            this.checklistOrder = checklist.getChecklistOrder();
            this.approvalTime = checklist.getApprovalDate();
        }
    }
}
