package com.seveneleven.devlens.domain.admin.repository;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.PutProject;
import com.seveneleven.devlens.domain.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PutProjectResponseConverter implements EntityConverter<PutProject.Response, Project> {
    @Override
    public PutProject.Response toDTO(Project project) {
        return new PutProject.Response(
                project.getId(),
                project.getProjectName(),
                project.getCustomer().getId(),
                project.getDeveloper().getId(),
                project.getProjectDescription(),
                project.getProjectType().getId(),
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
    public Project toEntity(PutProject.Response response) {
        //미사용 메서드
        return null;
    }
}
