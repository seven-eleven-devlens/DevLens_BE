package com.seveneleven.project.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class GetProjectDetail {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private ProjectDetail projectDetail;
        private List<ProjectStepInfo> projectStep;
        private List<ChecklistApplicationList> checklistApplicationList;
    }

    @Getter
    public static class ProjectDetail {
        private Long projectId;
        private String projectTypeName;
        private String projectName;
        private String projectDescription;
        private String projectContact;
        private String projectContactPhone;
        private String projectImageURL;

        public ProjectDetail(
                Long projectId,
                String projectTypeName,
                String projectName,
                String projectDescription,
                String name,
                String phoneNumber
        ) {
            this.projectId = projectId;
            this.projectTypeName = projectTypeName;
            this.projectName = projectName;
            this.projectDescription = projectDescription;
            this.projectContact = name;
            this.projectContactPhone = phoneNumber;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectStepInfo {
        private Long stepId;
        private String stepName;
        private Double stepProcessRate;
    }

    @Getter
    @Setter
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
