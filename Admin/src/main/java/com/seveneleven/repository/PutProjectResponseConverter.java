package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.PutProject;
import com.seveneleven.entity.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PutProjectResponseConverter implements EntityConverter<PutProject.Response, Project> {
    @Override
    public PutProject.Response toDTO(Project project) {
        return PutProject.Response.of(
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
    public Project toEntity(PutProject.Response response) {
        //미사용 메서드
        return null;
    }
}
