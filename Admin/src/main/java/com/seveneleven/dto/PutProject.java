package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PutProject {
    @Getter
    public static class Request {
        private Long id;
        private String projectName; // 프로젝트명
        private Long customerId; // 고객사 ID (Company 엔티티의 ID)
        private Long developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private Project.ProjectStatusCode projectStatusCode; //
        private Long bnsManagerId; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
        private LocalDateTime finalApprovalDate; // 최종 결재일시

        public Project updateProject(Project project, Company customer, Company developer, ProjectType projectType, Member bnsManager) {
            return project.update(
                    projectName,
                    projectDescription,
                    projectStatusCode,
                    contractNumber,
                    plannedStartDate,
                    plannedEndDate,
                    startDate,
                    endDate,
                    finalApprovalDate,
                    customer,
                    developer,
                    projectType,
                    bnsManager
            );
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private String projectName; // 프로젝트명
        private String customerId; // 고객사 ID (Company 엔티티의 ID)
        private String developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private String projectType; // 프로젝트 유형 ID
        private Project.ProjectStatusCode projectStatusCode; //
        private String bnsManagerId; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
        private LocalDateTime finalApprovalDate; // 최종 결재일시

        private Response(Project project) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.customerId = project.getCustomer().getCompanyName();
            this.developerId = project.getDeveloper().getCompanyName();
            this.projectDescription = project.getProjectDescription();
            this.projectType = project.getProjectType().getProjectTypeName();
            this.bnsManagerId = project.getBnsManager().getName();
            this.contractNumber = project.getContractNumber();
            this.plannedStartDate = project.getPlannedStartDate();
            this.plannedEndDate = project.getPlannedEndDate();
            this.startDate = project.getStartDate(); // 시작일
            this.endDate = project.getEndDate(); //종료일
            this.finalApprovalDate = project.getFinalApprovalDate(); // 최종 결재일시
        }

        public static Response of(Project project) {
            return new Response(project);
        }
    }
}
