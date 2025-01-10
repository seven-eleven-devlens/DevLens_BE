package com.seveneleven.dto;

import com.seveneleven.entity.project.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostProject {
    @Getter
    public static class Request {
        private String projectName; // 프로젝트명
        private Long customerId; // 고객사 ID (Company 엔티티의 ID)
        private Long developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private Long bnsManagerId; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDateTime plannedStartDate; // 시작 예정일
        private LocalDateTime plannedEndDate; // 종료 예정일

        public Request(
                String projectName,
                Long customerId,
                Long developerId,
                String projectDescription,
                Long projectTypeId,
                Long bnsManagerId,
                String contractNumber,
                LocalDateTime plannedStartDate,
                LocalDateTime plannedEndDate
        ) {
            this.projectName = projectName;
            this.customerId = customerId;
            this.developerId = developerId;
            this.projectDescription = projectDescription;
            this.projectTypeId = projectTypeId;
            this.bnsManagerId = bnsManagerId;
            this.contractNumber = contractNumber;
            this.plannedStartDate = plannedStartDate;
            this.plannedEndDate = plannedEndDate;
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
        private Project.projectStatusCode projectStatusCode; // 프로젝트 상태 코드
        private Long bnsManagerId; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDateTime plannedStartDate; // 시작 예정일
        private LocalDateTime plannedEndDate; // 종료 예정일

        public Response(
                Long id,
                String projectName,
                Long customerId,
                Long developerId,
                String projectDescription,
                Long projectTypeId,
                Project.projectStatusCode projectStatusCode,
                Long bnsManager,
                String contractNumber,
                LocalDateTime plannedStartDate,
                LocalDateTime plannedEndDate
        ) {
            this.id = id;
            this.projectName = projectName;
            this.customerId = customerId;
            this.developerId = developerId;
            this.projectDescription = projectDescription;
            this.projectTypeId = projectTypeId;
            this.projectStatusCode = projectStatusCode;
            this.bnsManagerId = bnsManager;
            this.contractNumber = contractNumber;
            this.plannedStartDate = plannedStartDate;
            this.plannedEndDate = plannedEndDate;
        }
    }
}
