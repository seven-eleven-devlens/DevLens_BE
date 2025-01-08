package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.global.entity.YesNo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectHistoryDto {
    @Getter
    public static class ProjectHistoryResponse {
        private Long id;
        private String projectName;
        private String customerName;
        private String developerName;
        private String description;
        private String statusCode;
        private String typeName;
        private Long bnsMangerId;
        private YesNo hasImage;
        private String contractNumber;
        private LocalDateTime plannedStartDate;
        private LocalDateTime startDate;
        private LocalDateTime plannedEndDate;
        private LocalDateTime endDate;
        public ProjectHistoryResponse(
                Long id,
                String projectName,
                String customerName,
                String developerName,
                String description,
                String statusCode,
                String typeName,
                Long bnsMangerId,
                YesNo hasImage,
                String contractNumber,
                LocalDateTime plannedStartDate,
                LocalDateTime startDate,
                LocalDateTime plannedEndDate,
                LocalDateTime endDate
        ) {
            this.id = id;
            this.projectName = projectName;
            this.customerName = customerName;
            this.developerName = developerName;
            this.description = description;
            this.statusCode = statusCode;
            this.typeName = typeName;
            this.bnsMangerId = bnsMangerId;
            this.hasImage = hasImage;
            this.contractNumber = contractNumber;
            this.plannedStartDate = plannedStartDate;
            this.startDate = startDate;
            this.plannedEndDate = plannedEndDate;
            this.endDate = endDate;
        }
    }
}
