package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PutProjectStepPosition {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long stepId;
        private String stepName;
        private Integer stepOrder;

        @Override
        public String toString() {
            return "PutProjectStepPosition.Response {" +
                    "stepId=" + stepId +
                    '}';
        }

        private Response(ProjectStep projectStep, Integer order) {
            stepId = projectStep.getId();
            stepName = projectStep.getStepName();
            stepOrder = order;
        }

        public static Response toDto(ProjectStep projectStep, Integer order) {
            return new Response(projectStep, order);
        }
    }

}
