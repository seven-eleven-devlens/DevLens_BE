package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.ProjectHistoryDto;
import com.seveneleven.devlens.domain.project.entity.ProjectHistory;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProjectHistoryConverter implements EntityConverter<ProjectHistoryDto.ProjectHistoryResponse, ProjectHistory>{
    @Override
    public ProjectHistoryDto.ProjectHistoryResponse toDTO(ProjectHistory projectHistory) {
        return new ProjectHistoryDto.ProjectHistoryResponse(
                projectHistory.getId(),
                projectHistory.getProjectName(),
                projectHistory.getCustomerName(),
                projectHistory.getDeveloperName(),
                projectHistory.getDescription(),
                projectHistory.getStatusCode(),
                projectHistory.getTypeName(),
                projectHistory.getBnsManagerId(),
                projectHistory.getHasImage(),
                projectHistory.getContractNumber(),
                projectHistory.getPlannedStartDate(),
                projectHistory.getPlannedStartDate(),
                projectHistory.getPlannedEndDate(),
                projectHistory.getEndDate()
        );
    }

    @Override
    public ProjectHistory toEntity(ProjectHistoryDto.ProjectHistoryResponse projectHistoryResponse) {
        return null;
    }
}
