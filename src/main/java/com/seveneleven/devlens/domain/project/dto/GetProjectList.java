package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.domain.project.entity.Project;
import lombok.AllArgsConstructor;
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
    @AllArgsConstructor
    public static class Response {
        List<GetMyProjectResponseInfo> myProjects;
        List<GetCompanyProjectResponseInfo> companyProjects;
    }

    @Getter
    @NoArgsConstructor
    public static class GetMyProjectResponseInfo {
        private Long projectId;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long customerCompanyId;
        private String customerCompanyName;

        private GetMyProjectResponseInfo(Project project) {
            this.projectId = project.getId();
            this.name = project.getProjectName();
            this.startDate = project.getStartDate().toLocalDate();
            this.endDate = project.getEndDate().toLocalDate();
            this.customerCompanyId = project.getCustomer().getId();
            this.customerCompanyName = project.getCustomer().getCompanyName();
        }

        public static List<GetMyProjectResponseInfo> toDto(List<Project> project) {
            return project.stream()
                    .map(GetMyProjectResponseInfo::new)
                    .toList();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCompanyProjectResponseInfo {
        private Long projectId;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long customerCompanyId;
        private String customerCompanyName;

        private GetCompanyProjectResponseInfo(Project project) {
            this.projectId = project.getId();
            this.name = project.getProjectName();
            this.startDate = project.getStartDate().toLocalDate();
            this.endDate = project.getEndDate().toLocalDate();
            this.customerCompanyId = project.getCustomer().getId();
            this.customerCompanyName = project.getCustomer().getCompanyName();
        }

        public static List<GetCompanyProjectResponseInfo> toDto(List<Project> project) {
            return project.stream()
                    .map(GetCompanyProjectResponseInfo::new)
                    .toList();
        }
    }
}
