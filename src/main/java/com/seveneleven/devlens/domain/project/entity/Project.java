package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.domain.admin.dto.PutProject;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import com.seveneleven.devlens.global.entity.YesNo;
import com.seveneleven.devlens.global.entity.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 프로젝트 ID

    private String projectName; // 프로젝트명

    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Company customer; // 고객사 ID

    @JoinColumn(name = "developer_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Company developer; // 개발사 ID

    @Column(columnDefinition = "TEXT")
    private String projectDescription; // 프로젝트 설명

    @JoinColumn(name = "project_type_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectType projectType; // 프로젝트 유형 ID

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private projectStatusCode projectStatusCode; // 프로젝트 상태 코드 (준비, 진행, 완료, 보류, 취소)

    @JoinColumn(name = "bns_manager_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member bnsManager; // BNS 담당자 ID

    private String contractNumber; // 계약서 번호

    private LocalDateTime plannedStartDate; // 시작 예정일

    private LocalDateTime plannedEndDate; // 종료 예정일

    private LocalDateTime startDate; // 시작일

    private LocalDateTime endDate; // 종료일

    private Long finalApprover; // 최종 결재자

    private LocalDateTime finalApprovalDate; // 최종 결재일시

    public Project(
            Long id,
            String projectName,
            Company customer,
            Company developer,
            String projectDescription,
            ProjectType projectTypeId,
            projectStatusCode projectStatusCode,
            Member bnsManager,
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
        this.customer = customer;
        this.developer = developer;
        this.projectDescription = projectDescription;
        this.projectType = projectTypeId;
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

    // Dto를 받는 생성자
    public Project(
            String projectName,
            Company customer,
            Company developer,
            String projectDescription,
            ProjectType projectTypeId,
            projectStatusCode projectStatusCode,
            Member bnsManager,
            String contractNumber,
            LocalDateTime plannedStartDate,
            LocalDateTime plannedEndDate
    ) {
        this.projectName = projectName;
        this.customer = customer;
        this.developer = developer;
        this.projectDescription = projectDescription;
        this.projectType = projectTypeId;
        this.projectStatusCode = projectStatusCode;
        this.bnsManager = bnsManager;
        this.contractNumber = contractNumber;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
    }

    public Project update(
            PutProject.Request request,
            Company customer,
            Company developer,
            ProjectType ProjectType,
            Member bnsManager
    ) {
        this.projectName = request.getProjectName();
        this.customer = customer;
        this.developer = developer;
        this.projectDescription = request.getProjectDescription();
        this.projectType = ProjectType;
        this.projectStatusCode = request.getProjectStatusCode();
        this.bnsManager = bnsManager;
        this.hasImage = request.getHasImage();
        this.contractNumber = request.getContractNumber();
        this.plannedStartDate = request.getPlannedStartDate();
        this.plannedEndDate = request.getPlannedEndDate();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.finalApprover = request.getFinalApprover();
        this.finalApprovalDate = request.getFinalApprovalDate();
        return this;
    }

    public enum projectStatusCode {
        PREPARED,
        IN_PROGRESS,
        COMPLETED,
        CLOSED,
        CANCELLED
    }
}
