package com.seveneleven.common;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum BasicStep {
    STEP_ONE("기획", "기획 단계입니다.", 1),
    STEP_TWO("디자인", "디자인 단계입니다.", 2),
    STEP_THREE("개발", "개발 단계입니다.", 3),
    STEP_FOUR("QA", "QA 단계입니다.", 4),
    STEP_FIVE("유지보수", "유지보수 단계입니다.", 5);

    private final String stepName;
    private final String description;
    private final Integer stepOrder;

    // 기존의 create 메서드를 활용하여 ProjectStep 객체 생성
    public ProjectStep create(Project project) {
        return ProjectStep.create(project, this.stepName, this.description, this.stepOrder);
    }

    public static List<ProjectStep> createSteps(Project project) {
        List<ProjectStep> steps = new ArrayList<>();
        for (BasicStep stepEnum : values()) {
            steps.add(stepEnum.create(project));
        }
        return steps;
    }
}
