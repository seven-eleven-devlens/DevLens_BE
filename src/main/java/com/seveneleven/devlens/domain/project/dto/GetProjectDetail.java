package com.seveneleven.devlens.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

public class GetProjectDetail {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @ToString
    public static class Response {
        private Long projectId;
        private String projectType;
        private String projectName;
        private String projectDescription;
        private String projectContact;
        private String projectContactPhone;
        private String projectImageURL;
        private List<ProjectStepInfo> projectStep;
        private List<ChecklistApplicationList> checklistApplicationList;

        public Response(
                Long projectId,
                String projectType,
                String projectName,
                String projectDescription,
                String projectContact,
                String projectContactPhone
        ) {
            this.projectId = projectId;
            this.projectType = projectType;
            this.projectName = projectName;
            this.projectDescription = projectDescription;
            this.projectContact = projectContact;
            this.projectContactPhone = projectContactPhone;
        }

        public void setProjectStep(List<ProjectStepInfo> projectStep) {
            this.projectStep = projectStep;
        }

        public void setChecklistApplicationList(List<ChecklistApplicationList> checklistApplicationList) {
            this.checklistApplicationList = checklistApplicationList;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ProjectStepInfo {
        private Long stepId;
        private String stepName;
        private Double stepProcessRate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ChecklistApplicationList {
        private Long checklistApplicationId;
        private String StepName;
        private String checklistName;
        private String applicationTitle;
        private String applicationUserName;
        private LocalDateTime ApplicationDateTime;
    }
}
