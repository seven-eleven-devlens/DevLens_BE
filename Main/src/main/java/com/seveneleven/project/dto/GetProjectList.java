package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GetProjectList {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        List<GetMyProjectResponseInfo> myProjects;

        private Response(List<GetMyProjectResponseInfo> myProjects) {
            this.myProjects = myProjects;
        }

        public static GetProjectList.Response toDto(List<GetMyProjectResponseInfo> myProjects) {
            return new GetProjectList.Response(myProjects);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class GetMyProjectResponseInfo {
        private Long id;
        private String projectName;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long customerId;
        private String customerCompanyName;
        private String currentStepName;

        private GetMyProjectResponseInfo(Project project) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.customerId = project.getCustomer().getId();
            this.customerCompanyName = project.getCustomer().getCompanyName();
            this.currentStepName = project.getCurrentProjectStep();
        }

        public static List<GetMyProjectResponseInfo> toDto(List<Project> project) {
            return project.stream()
                    .map(GetMyProjectResponseInfo::new)
                    .toList();
        }
    }
}
