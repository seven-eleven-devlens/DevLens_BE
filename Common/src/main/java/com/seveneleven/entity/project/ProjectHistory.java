package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_history")
public class ProjectHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 관리 번호

    @Column(nullable = false)
    private String projectName; // 프로젝트명

    @Column(nullable = false)
    private String customerName; // 고객사 이름

    @Column(nullable = false, length = 255)
    private String developerName; // 개발사 이름

    @Column(columnDefinition = "TEXT")
    private String description; // 프로젝트 설명

    @Column(nullable = false)
    private String statusCode; // 프로젝트 상태 코드 (준비, 진행, 완료, 보류, 취소)

    @Column(nullable = false)
    private String typeName; // 프로젝트 유형명

    private Long bnsManagerId; // BNS 담당자 ID

    @Column(nullable = false)
    private YesNo hasImage; // 이미지 파일 유무

    private String contractNumber; // 계약서 번호

    private LocalDate plannedStartDate; // 시작 예정일

    private LocalDate startDate; // 시작일

    private LocalDate plannedEndDate; // 종료 예정일

    private LocalDate endDate; // 종료일

    public ProjectHistory(
            String projectName,
            String customerName,
            String developerName,
            String description,
            String statusCode,
            String typeName,
            Long bnsManagerId,
            YesNo hasImage,
            String contractNumber,
            LocalDate plannedStartDate,
            LocalDate startDate,
            LocalDate plannedEndDate,
            LocalDate endDate
    ) {
        this.projectName = projectName;
        this.customerName = customerName;
        this.developerName = developerName;
        this.description = description;
        this.statusCode = statusCode;
        this.typeName = typeName;
        this.bnsManagerId = bnsManagerId;
        this.hasImage = hasImage;
        this.contractNumber = contractNumber;
        this.plannedStartDate = plannedStartDate;
        this.startDate = startDate;
        this.plannedEndDate = plannedEndDate;
        this.endDate = endDate;
    }

    public ProjectHistory(Project project) {
        this(
                project.getProjectName(),
                project.getCustomer().getCompanyName(),
                project.getDeveloper().getCompanyName(),
                project.getProjectDescription(),
                project.getProjectStatusCode().name(),
                project.getProjectType().getProjectTypeName(),
                project.getBnsManager().getId(),
                project.getHasImage(),
                project.getContractNumber(),
                project.getPlannedStartDate(),
                project.getStartDate(),
                project.getPlannedEndDate(),
                project.getEndDate()
        );
    }
}