package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.PutProject;
import com.seveneleven.devlens.domain.project.entity.Project;

public class PutProjectRequestConverter implements EntityConverter<PutProject.Request, Project> {
    @Override
    public PutProject.Request toDTO(Project project) {
        return new PutProject.Request(
                project.getId(),
                project.getProjectName(),
                project.getCustomer().getId(),
                project.getDeveloper().getId(),
                project.getProjectDescription(),
                project.getProjectTypeId().getId(),
                project.getProjectStatusCode(),
                project.getBnsManager().getId(),
                project.getHasImage(),
                project.getContractNumber(),
                project.getPlannedStartDate(),
                project.getPlannedEndDate(),
                project.getStartDate(),
                project.getEndDate(),
                project.getFinalApprover(),
                project.getFinalApprovalDate()
        );
    }

    @Override
    public Project toEntity(PutProject.Request request) {
        // 미사용 메서드
        return null;
    }
}
