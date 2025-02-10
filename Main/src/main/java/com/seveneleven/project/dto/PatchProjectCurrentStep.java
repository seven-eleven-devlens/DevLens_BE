package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PatchProjectCurrentStep {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectId;
        private String stepName;

        private Response(Project project) {
            this.projectId = project.getId();
            this.stepName = project.getCurrentProjectStep();
        }

        public static Response toDto(Project project) {
            return new Response(project);
        }
    }
}
