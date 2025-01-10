package com.seveneleven.dto;

import com.seveneleven.entity.project.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetProject {
    @Getter
    public static class Request {
        private Long id; // 프로젝트 ID
        private String projectName; // 프로젝트명
        private Long customerId; // 고객사 ID (Company 엔티티의 ID)
        private Long developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private Project.ProjectStatusCode projectStatusCode; // 프로젝트 상태 코드
        private Long bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
        private Long finalApprover; // 최종 결재자
        private LocalDateTime finalApprovalDate; // 최종 결재일시

        public Request(
                Long id,
                String projectName,
                Long customerId,
                Long developerId,
                String projectDescription,
                Long projectTypeId,
                Project.ProjectStatusCode projectStatusCode,
                Long bnsManager,
                String contractNumber,
                LocalDate plannedStartDate,
                LocalDate plannedEndDate,
                LocalDate startDate,
                LocalDate endDate,
                Long finalApprover,
                LocalDateTime finalApprovalDate
        ) {
            this.id = id;
            this.projectName = projectName;
            this.customerId = customerId;
            this.developerId = developerId;
            this.projectDescription = projectDescription;
            this.projectTypeId = projectTypeId;
            this.projectStatusCode = projectStatusCode;
            this.bnsManager = bnsManager;
            this.contractNumber = contractNumber;
            this.plannedStartDate = plannedStartDate;
            this.plannedEndDate = plannedEndDate;
            this.startDate = startDate;
            this.endDate = endDate;
            this.finalApprover = finalApprover;
            this.finalApprovalDate = finalApprovalDate;
        }
    }

    @Getter
    public static class Response {
        private Long id; // 프로젝트 ID
        private String projectName; // 프로젝트명
        private Long customerId; // 고객사 ID (Company 엔티티의 ID)
        private Long developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private Project.ProjectStatusCode projectStatusCode; // 프로젝트 상태 코드
        private Long bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
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
                Long bnsManager,
                String contractNumber,
                LocalDate plannedStartDate,
                LocalDate plannedEndDate,
                LocalDate startDate,
                LocalDate endDate,
                Long finalApprover,
                LocalDateTime finalApprovalDate
        ) {
            this.id = id;
            this.projectName = projectName;
            this.customerId = customerId;
            this.developerId = developerId;
            this.projectDescription = projectDescription;
            this.projectTypeId = projectTypeId;
            this.projectStatusCode = projectStatusCode;
            this.bnsManager = bnsManager;
            this.contractNumber = contractNumber;
            this.plannedStartDate = plannedStartDate;
            this.plannedEndDate = plannedEndDate;
            this.startDate = startDate;
            this.endDate = endDate;
            this.finalApprover = finalApprover;
            this.finalApprovalDate = finalApprovalDate;
        }
    }
}
