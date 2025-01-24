package com.seveneleven.dto;

import com.seveneleven.entity.project.ProjectHistory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetProjectHistory {
    @Getter
    public static class Response {
        private Long id;
        private String projectName;
        private String customerName;
        private String developerName;
        private String description;
        private String statusCode;
        private String typeName;
        private String bnsManager;
        private String contractNumber;
        private LocalDate plannedStartDate;
        private LocalDate startDate;
        private LocalDate plannedEndDate;
        private LocalDate endDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;

        private Response(
                ProjectHistory projectHistory,
                String createdBy,
                String updatedBy
        ) {
            this.id = projectHistory.getId();
            this.projectName = projectHistory.getProjectName();
            this.customerName = projectHistory.getCustomerName();
            this.developerName = projectHistory.getDeveloperName();
            this.description = projectHistory.getDescription();
            this.statusCode = projectHistory.getStatusCode();
            this.typeName = projectHistory.getTypeName();
            this.bnsManager = projectHistory.getBnsManager();
            this.contractNumber = projectHistory.getContractNumber();
            this.plannedStartDate = projectHistory.getPlannedStartDate();
            this.startDate = projectHistory.getStartDate();
            this.plannedEndDate = projectHistory.getPlannedEndDate();
            this.endDate = projectHistory.getEndDate();
            this.createdAt = projectHistory.getCreatedAt();
            this.updatedAt = projectHistory.getUpdatedAt();
            this.createdBy = createdBy;
            this.updatedBy = updatedBy;
        }

        public Response(
                ProjectHistory projectHistory
        ) {
            this.id = projectHistory.getId();
            this.projectName = projectHistory.getProjectName();
            this.customerName = projectHistory.getCustomerName();
            this.developerName = projectHistory.getDeveloperName();
            this.description = projectHistory.getDescription();
            this.statusCode = projectHistory.getStatusCode();
            this.typeName = projectHistory.getTypeName();
            this.bnsManager = projectHistory.getBnsManager();
            this.contractNumber = projectHistory.getContractNumber();
            this.plannedStartDate = projectHistory.getPlannedStartDate();
            this.startDate = projectHistory.getStartDate();
            this.plannedEndDate = projectHistory.getPlannedEndDate();
            this.endDate = projectHistory.getEndDate();
            this.createdAt = projectHistory.getCreatedAt();
            this.updatedAt = projectHistory.getUpdatedAt();
            this.createdBy = null;
            this.updatedBy = null;
        }

        public static Response from(ProjectHistory projectHistory) {
            return new Response(projectHistory);
        }
        public static Response of(
                ProjectHistory project,
                String createdBy,
                String updatedBy
        ){
            return new Response(
                    project,
                    createdBy,
                    updatedBy
            );
        }
    }
}
