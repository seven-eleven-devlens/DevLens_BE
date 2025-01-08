package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.global.entity.YesNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class GetProjectStep {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectId;
        private List<ProjectStepInfo> projectStepInfo;

        public Response(Long projectId, List<ProjectStepInfo> projectStepInfo) {
            this.projectId = projectId;
            this.projectStepInfo = projectStepInfo;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectStepInfo {
        private Long stepId;
        private String stepName;
        private List<ProjectChecklist> projectChecklist;

        public void setProjectChecklist(List<ProjectChecklist> checklists) {
            this.projectChecklist = checklists;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectChecklist {
        private Long checklistId;
        private String checklistName;
        private YesNo checklistStatus;
        private LocalDateTime approvalTime;
    }
}
