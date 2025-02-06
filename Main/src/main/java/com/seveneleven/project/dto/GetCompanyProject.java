package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GetCompanyProject {

    @Getter
    @NoArgsConstructor
    public static class Response {
        Long companyId;
        List<CompanyProject> companyProjects;

        private Response(Long companyId, List<CompanyProject> companyProjects) {
            this.companyId = companyId;
            this.companyProjects = companyProjects;
        }

        public static Response toDto(Long companyId, List<CompanyProject> projects) {
            return new Response(companyId, projects);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompanyProject {
        private Long id;
        private String projectName;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long customerId;
        private String customerName;

        private CompanyProject(Project project) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.customerId = project.getCustomer().getId();
            this.customerName = project.getCustomer().getCompanyName();
        }

        public static List<CompanyProject> toDto(List<Project> project) {
            return project.stream()
                    .map(CompanyProject::new)
                    .toList();
        }
    }
}
