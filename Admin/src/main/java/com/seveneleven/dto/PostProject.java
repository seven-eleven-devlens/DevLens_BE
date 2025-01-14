package com.seveneleven.dto;

import com.seveneleven.entity.project.Project;
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
        private Long bnsManagerId; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일

        private Request(
                String projectName,
                Long customerId,
                Long developerId,
                String projectDescription,
                Long projectTypeId,
                Long bnsManagerId,
                String contractNumber,
                LocalDate plannedStartDate,
                LocalDate plannedEndDate
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
                Long id,
                String projectName,
                String customerName,
                String developerName,
                String projectDescription,
                String projectTypeName,
                Project.ProjectStatusCode projectStatusCode,
                String bnsManagerName,
                String contractNumber,
                LocalDate plannedStartDate,
                LocalDate plannedEndDate
        ) {
            this.id = id;
            this.projectName = projectName;
            this.customerName = customerName;
            this.developerName = developerName;
            this.projectDescription = projectDescription;
            this.projectTypeName = projectTypeName;
            this.projectStatusCode = projectStatusCode;
            this.bnsManagerName = bnsManagerName;
            this.contractNumber = contractNumber;
            this.plannedStartDate = plannedStartDate;
            this.plannedEndDate = plannedEndDate;
        }

        public static Response of(
                Project project
        ) {
            return new Response(
                    project.getId(),
                    project.getProjectName(),
                    project.getCustomer().getCompanyName(),
                    project.getDeveloper().getCompanyName(),
                    project.getProjectDescription(),
                    project.getProjectType().getProjectTypeName(),
                    project.getProjectStatusCode(),
                    project.getBnsManager().getName(),
                    project.getContractNumber(),
                    project.getPlannedStartDate(),
                    project.getPlannedEndDate()
            );
        }
    }
}
