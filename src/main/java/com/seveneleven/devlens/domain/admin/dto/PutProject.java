package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.domain.project.entity.Project;
import com.seveneleven.devlens.global.entity.YesNo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        private Project.projectStatusCode projectStatusCode; //
        private Long bnsManagerId; // BNS 담당자 ID (Member 엔티티의 ID)
        private YesNo hasImage;
        private String contractNumber; // 계약서 번호
        private LocalDateTime plannedStartDate; // 시작 예정일
        private LocalDateTime plannedEndDate; // 종료 예정일
        private LocalDateTime startDate; // 시작일
        private LocalDateTime endDate; // 종료일
        private Long finalApprover; // 최종 결재자
        private LocalDateTime finalApprovalDate; // 최종 결재일시

        public Request(
                Long id,
                String projectName,
                Long customerId,
                Long developerId,
                String projectDescription,
                Long projectTypeId,
                Project.projectStatusCode projectStatusCode,
                Long bnsManagerId,
                YesNo hasImage,
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
            this.hasImage = hasImage;
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
        private Project.projectStatusCode projectStatusCode; //
        private Long bnsManagerId; // BNS 담당자 ID (Member 엔티티의 ID)
        private YesNo hasImage;
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
                Project.projectStatusCode projectStatusCode,
                Long bnsManagerId,
                YesNo hasImage,
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
            this.hasImage = hasImage;
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
