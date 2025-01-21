package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.GetProjectHistory;
import com.seveneleven.entity.project.ProjectHistory;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProjectHistoryConverter implements EntityConverter<GetProjectHistory.Response, ProjectHistory> {
    @Override
    public GetProjectHistory.Response toDTO(ProjectHistory projectHistory) {
        return GetProjectHistory.Response.from(projectHistory);
    }

    public GetProjectHistory.Response toDTO(ProjectHistory projectHistory, String createdBy, String updatedBy) {
        return GetProjectHistory.Response.of(
                projectHistory,
                createdBy,
                updatedBy
        );
    }

    @Override
    public ProjectHistory toEntity(GetProjectHistory.Response response) {
        //사용하지 않는 함수라 구현하지 않음
        return null;
    }
}
