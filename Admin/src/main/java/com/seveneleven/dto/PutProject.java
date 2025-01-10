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
        private Long finalApprover; // 최종 결재자
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
                    finalApprover,
                    finalApprovalDate,
                    customer,
                    developer,
                    projectType,
                    bnsManager
            );
        }

        public Request(
                Long id,
                String projectName,
                Long customerId,
                Long developerId,
                String projectDescription,
                Long projectTypeId,
                Project.ProjectStatusCode projectStatusCode,
                Long bnsManagerId,
                String contractNumber,
                LocalDate plannedStartDate,
                LocalDate plannedEndDate,
                LocalDate startDate, // 시작일
                LocalDate endDate, // 종료일
                Long finalApprover, // 최종 결재자
                LocalDateTime finalApprovalDate // 최종 결재일시
        ) {
            this.id = id;
            this.projectName = projectName;
            this.customerId = customerId;
            this.developerId = developerId;
            this.projectDescription = projectDescription;
            this.projectTypeId = projectTypeId;
            this.projectStatusCode = projectStatusCode;
            this.bnsManagerId = bnsManagerId;
            this.contractNumber = contractNumber;
            this.plannedStartDate = plannedStartDate;
            this.plannedEndDate = plannedEndDate;
            this.startDate = startDate; // 시작일
            this.endDate = endDate; //종료일
            this.finalApprover = finalApprover; // 최종 결재자
            this.finalApprovalDate = finalApprovalDate; // 최종 결재일시
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private String projectName; // 프로젝트명
        private Long customerId; // 고객사 ID (Company 엔티티의 ID)
        private Long developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private Project.ProjectStatusCode projectStatusCode; //
        private Long bnsManagerId; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDateTime plannedStartDate; // 시작 예정일
        private LocalDateTime plannedEndDate; // 종료 예정일
        private LocalDateTime startDate; // 시작일
        private LocalDateTime endDate; // 종료일
        private Long finalApprover; // 최종 결재자
        private LocalDateTime finalApprovalDate; // 최종 결재일시

        public Response(
                Long id,
                String projectName,
                Long customerId,
                Long developerId,
                String projectDescription,
                Long projectTypeId,
                Project.ProjectStatusCode projectStatusCode,
                Long bnsManagerId,
                String contractNumber,
                LocalDateTime plannedStartDate,
                LocalDateTime plannedEndDate,
                LocalDateTime startDate, // 시작일
                LocalDateTime endDate, // 종료일
                Long finalApprover, // 최종 결재자
                LocalDateTime finalApprovalDate // 최종 결재일시
        ) {
            this.id = id;
            this.projectName = projectName;
            this.customerId = customerId;
            this.developerId = developerId;
            this.projectDescription = projectDescription;
            this.projectTypeId = projectTypeId;
            this.bnsManagerId = bnsManagerId;
            this.contractNumber = contractNumber;
            this.plannedStartDate = plannedStartDate;
            this.plannedEndDate = plannedEndDate;
            this.startDate = startDate; // 시작일
            this.endDate = endDate; //종료일
            this.finalApprover = finalApprover; // 최종 결재자
            this.finalApprovalDate = finalApprovalDate; // 최종 결재일시
        }
    }
}
