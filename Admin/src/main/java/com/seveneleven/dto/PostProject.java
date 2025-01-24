package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostProject {
    @Getter
    public static class Request {
        private String projectName; // 프로젝트명
        private Long customerId; // 고객사 ID (Company 엔티티의 ID)
        private Long developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private String bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일

        public Project toEntity(
                Company customer,
                Company developer,
                ProjectType projectType
        ) {
            return Project.create(
                    projectName,
                    customer,
                    developer,
                    projectDescription,
                    projectType,
                    Project.ProjectStatusCode.PREPARED,
                    bnsManager,
                    contractNumber,
                    plannedStartDate,
                    plannedEndDate
            );
        }
    }

    @Getter
    public static class Response {
        private Long id; // 프로젝트 ID
        private String projectName; // 프로젝트명
        private String customerName; // 고객사 ID (Company 엔티티의 ID)
        private String developerName; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private String projectTypeName; // 프로젝트 유형
        private Project.ProjectStatusCode projectStatusCode; // 프로젝트 상태 코드
        private String bnsManagerName; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일

        private Response(
                Project project
        ) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.customerName = project.getCustomer().getCompanyName();
            this.developerName = project.getDeveloper().getCompanyName();
            this.projectDescription = project.getProjectDescription();
            this.projectTypeName = project.getProjectType().getProjectTypeName();
            this.projectStatusCode = project.getProjectStatusCode();
            this.bnsManagerName = project.getBnsManager();
            this.contractNumber = project.getContractNumber();
            this.plannedStartDate = project.getPlannedStartDate();
            this.plannedEndDate = project.getPlannedEndDate();
        }

        public static Response of(Project project) {
            return new Response(project);
        }
    }
}
