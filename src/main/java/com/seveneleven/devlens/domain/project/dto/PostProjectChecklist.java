package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.domain.project.entity.Checklist;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import com.seveneleven.devlens.global.entity.YesNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

public class PostProjectChecklist {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long ProjectStepId;
        private String checklistTitle;
        private String checklistDescription;
        private List<File> files;
        private List<String> links;

        public Checklist toEntity(ProjectStep projectStep) {
            return new Checklist(this, projectStep);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long projectChecklistId;
        private String checklistTitle;
        private String checklistDescription;
        private YesNo isActive;
        private YesNo isChecked;

        public static PostProjectChecklist.Response toDto(Checklist checklist) {
            return new PostProjectChecklist.Response(
                    checklist.getId(),
                    checklist.getTitle(),
                    checklist.getDescription(),
                    checklist.getIsActive(),
                    checklist.getIsChecked()
            );
        }
    }
}
