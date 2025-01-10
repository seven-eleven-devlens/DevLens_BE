package com.seveneleven.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

        public Response(
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
                LocalDate endDate
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
        }
    }
}
