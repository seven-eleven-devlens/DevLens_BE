package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.ReadProjectHistory;
import com.seveneleven.entity.project.ProjectHistory;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ProjectHistoryConverter implements EntityConverter<ReadProjectHistory.Response, ProjectHistory> {
    @Override
    public ReadProjectHistory.Response toDTO(ProjectHistory projectHistory) {
        return null;
    }

    public ReadProjectHistory.Response toDTO(ProjectHistory projectHistory, String createdBy, String updatedBy) {
        return ReadProjectHistory.Response.of(
                projectHistory,
                createdBy,
                updatedBy
        );
    }

    @Override
    public ProjectHistory toEntity(ReadProjectHistory.Response response) {
        //사용하지 않는 함수라 구현하지 않음
        return null;
    }
}
