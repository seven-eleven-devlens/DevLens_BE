package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.domain.project.entity.Project;
import com.seveneleven.devlens.global.entity.YesNo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

    @NoArgsConstructor
    public class ProjectDto {
        @Getter
        public static class ProjectRequest {
            private Long id; // 프로젝트 ID
            private String projectName; // 프로젝트명
            private Long customerId; // 고객사 ID (Company 엔티티의 ID)
            private Long developerId; // 개발사 ID (Company 엔티티의 ID)
            private String projectDescription; // 프로젝트 설명
            private Long projectTypeId; // 프로젝트 유형 ID
            private Project.projectStatusCode projectStatusCode; // 프로젝트 상태 코드
            private Long bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
            private YesNo hasImage; // 이미지 여부
            private String contractNumber; // 계약서 번호
            private LocalDateTime plannedStartDate; // 시작 예정일
            private LocalDateTime plannedEndDate; // 종료 예정일
            private LocalDateTime startDate; // 시작일
            private LocalDateTime endDate; // 종료일
            private Long finalApprover; // 최종 결재자
            private LocalDateTime finalApprovalDate; // 최종 결재일시

            public ProjectRequest(
                Long id,
                String projectName,
                Long customerId,
                Long developerId,
                String projectDescription,
                Long projectTypeId,
                Project.projectStatusCode projectStatusCode,
                Long bnsManager,
                YesNo hasImage,
                String contractNumber,
                LocalDateTime plannedStartDate,
                LocalDateTime plannedEndDate,
                LocalDateTime startDate,
                LocalDateTime endDate,
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
                this.hasImage = hasImage;
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
        public static class ProjectResponse {
            private Long id; // 프로젝트 ID
            private String projectName; // 프로젝트명
            private Long customerId; // 고객사 ID (Company 엔티티의 ID)
            private Long developerId; // 개발사 ID (Company 엔티티의 ID)
            private String projectDescription; // 프로젝트 설명
            private Long projectTypeId; // 프로젝트 유형 ID
            private Project.projectStatusCode projectStatusCode; // 프로젝트 상태 코드
            private Long bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
            private YesNo hasImage; // 이미지 여부
            private String contractNumber; // 계약서 번호
            private LocalDateTime plannedStartDate; // 시작 예정일
            private LocalDateTime plannedEndDate; // 종료 예정일
            private LocalDateTime startDate; // 시작일
            private LocalDateTime endDate; // 종료일
            private Long finalApprover; // 최종 결재자
            private LocalDateTime finalApprovalDate; // 최종 결재일시

            public ProjectResponse(
                    Long id,
                    String projectName,
                    Long customerId,
                    Long developerId,
                    String projectDescription,
                    Long projectTypeId,
                    Project.projectStatusCode projectStatusCode,
                    Long bnsManager,
                    YesNo hasImage,
                    String contractNumber,
                    LocalDateTime plannedStartDate,
                    LocalDateTime plannedEndDate,
                    LocalDateTime startDate,
                    LocalDateTime endDate,
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
                this.hasImage = hasImage;
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
