package com.seveneleven.dto;

import com.seveneleven.entity.project.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCompanyProject {
    private Long id;
    private String projectName;
    private LocalDate plannedStartDate;
    private String developerName;
    private String customerName;
    private String projectStatus;

    private GetCompanyProject(
            Long id,
            String projectName,
            LocalDate plannedStartDate,
            String developerName,
            String customerName,
            String projectStatus
    ) {
        this.id = id;
        this.projectName = projectName;
        this.plannedStartDate = plannedStartDate;
        this.developerName = developerName;
        this.customerName = customerName;
        this.projectStatus = projectStatus;
    }
    public static GetCompanyProject of(
            Project project
    ) {
        return new GetCompanyProject(
                project.getId(),
                project.getProjectName(),
                project.getPlannedStartDate(),
                project.getDeveloper().getCompanyName(),
                project.getCustomer().getCompanyName(),
                project.getProjectStatusCode().name()
        );
    }

}
