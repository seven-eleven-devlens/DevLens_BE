package com.seveneleven.repository;


import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.PutProject;
import com.seveneleven.entity.project.Project;

public class PutProjectRequestConverter implements EntityConverter<PutProject.Request, Project> {
    @Override
    public PutProject.Request toDTO(Project project) {
        return PutProject.Request.of(
                project.getId(),
                project.getProjectName(),
                project.getCustomer().getId(),
                project.getDeveloper().getId(),
                project.getProjectDescription(),
                project.getProjectType().getId(),
                project.getProjectStatusCode(),
                project.getBnsManager().getId(),
                project.getContractNumber(),
                project.getPlannedStartDate(),
                project.getPlannedEndDate(),
                project.getStartDate(),
                project.getEndDate(),
                project.getFinalApproverId(),
                project.getFinalApprovalDate()
        );
    }

    @Override
    public Project toEntity(PutProject.Request request) {
        // 미사용 메서드
        return null;
    }
}
