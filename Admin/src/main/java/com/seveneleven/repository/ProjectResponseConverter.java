package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.GetProject;
import com.seveneleven.entity.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProjectResponseConverter implements EntityConverter<GetProject.Response, Project> {
    @Override
    public GetProject.Response toDTO(
            Project project
    ) {
        return GetProject.Response.of(project);
    }

    @Override
    public Project toEntity(
            GetProject.Response response
    ) {
        return null;
    }
}
