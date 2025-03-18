package com.seveneleven.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class PostProjectStep {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {
        String stepName;
        String stepDescription;
        Integer stepOrderNumber;
        @JsonProperty
        List<PostChecklist> checklists;

        @Override
        public String toString() {
            return "Request{" +
                    "stepName='" + stepName + '\'' +
                    '}';
        }

        public ProjectStep toEntity(Project project, Integer order) {
            return ProjectStep.create(project, stepName, stepDescription, order);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PostChecklist {
        String checklistTitle;
        String checklistDescription;

        @Override
        public String toString() {
            return "PostChecklist{" +
                    "checklistTitle='" + checklistTitle + '\'' +
                    '}';
        }

        public Checklist toEntity(ProjectStep projectStep, Integer order) {
            return Checklist.create(checklistTitle, checklistDescription, order, projectStep);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        Long projectId;
        String stepName;
        Integer stepOrderNumber;
        List<PostChecklist> checklists;

        @Override
        public String toString() {
            return "Response{" +
                    "projectId=" + projectId +
                    '}';
        }

        private Response(ProjectStep projectStep, List<PostChecklist> checklists) {
            this.projectId = projectStep.getProject().getId();
            this.stepName = projectStep.getStepName();
            this.stepOrderNumber = projectStep.getStepOrder();
            this.checklists = checklists;
        }

        public static Response toDto(ProjectStep projectStep, List<PostChecklist> checklists) {
            return new Response(projectStep, checklists);
        }
    }
}
