package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.ReadProjectHistory;
import com.seveneleven.devlens.domain.project.entity.ProjectHistory;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProjectHistoryConverter implements EntityConverter<ReadProjectHistory.Response, ProjectHistory> {
    @Override
    public ReadProjectHistory.Response toDTO(ProjectHistory projectHistory) {
        return new ReadProjectHistory.Response(
                projectHistory.getId(),
                projectHistory.getProjectName(),
                projectHistory.getCustomerName(),
                projectHistory.getDeveloperName(),
                projectHistory.getDescription(),
                projectHistory.getStatusCode(),
                projectHistory.getTypeName(),
                projectHistory.getBnsManagerId(),
                projectHistory.getContractNumber(),
                projectHistory.getPlannedStartDate(),
                projectHistory.getPlannedStartDate(),
                projectHistory.getPlannedEndDate(),
                projectHistory.getEndDate()
        );
    }

    @Override
    public ProjectHistory toEntity(ReadProjectHistory.Response response) {
        //사용하지 않는 함수라 구현하지 않음
        return null;
    }
}
