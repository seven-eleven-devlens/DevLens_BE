package com.seveneleven.dto;

import com.seveneleven.entity.project.ProjectHistory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadProjectHistory {
    @Getter
    public static class Response {
        private Long id;
        private String projectName;
        private String customerName;
        private String developerName;
        private String description;
        private String statusCode;
        private String typeName;
        private Long bnsManagerId;
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
                Long id,
                String projectName,
                String customerName,
                String developerName,
                String description,
                String statusCode,
                String typeName,
                Long bnsManagerId,
                String contractNumber,
                LocalDate plannedStartDate,
                LocalDate startDate,
                LocalDate plannedEndDate,
                LocalDate endDate,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                String createdBy,
                String updatedBy
        ) {
            this.id = id;
            this.projectName = projectName;
            this.customerName = customerName;
            this.developerName = developerName;
            this.description = description;
            this.statusCode = statusCode;
            this.typeName = typeName;
            this.bnsManagerId = bnsManagerId;
            this.contractNumber = contractNumber;
            this.plannedStartDate = plannedStartDate;
            this.startDate = startDate;
            this.plannedEndDate = plannedEndDate;
            this.endDate = endDate;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.createdBy = createdBy;
            this.updatedBy = updatedBy;
        }

        public static Response from(
                Response response,
                String createdBy,
                String updatedBy
        ){
            return new Response(
                    response.getId(),
                    response.getProjectName(),
                    response.getCustomerName(),
                    response.getDeveloperName(),
                    response.getDescription(),
                    response.getStatusCode(),
                    response.getTypeName(),
                    response.getBnsManagerId(),
                    response.getContractNumber(),
                    response.getPlannedStartDate(),
                    response.getStartDate(),
                    response.getPlannedEndDate(),
                    response.getEndDate(),
                    response.getCreatedAt(),
                    response.getUpdatedAt(),
                    createdBy,
                    updatedBy
            );
        }

        public static Response of(
                ProjectHistory project,
                String createdBy,
                String updatedBy
        ){
            return new Response(
                    project.getId(),
                    project.getProjectName(),
                    project.getCustomerName(),
                    project.getDeveloperName(),
                    project.getDescription(),
                    project.getStatusCode(),
                    project.getTypeName(),
                    project.getBnsManagerId(),
                    project.getContractNumber(),
                    project.getPlannedStartDate(),
                    project.getStartDate(),
                    project.getPlannedEndDate(),
                    project.getEndDate(),
                    project.getCreatedAt(),
                    project.getUpdatedAt(),
                    createdBy,
                    updatedBy
            );
        }
    }
}
