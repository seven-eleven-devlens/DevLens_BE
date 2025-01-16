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
        return PutProject.Response.of(project);
    }

    @Override
    public Project toEntity(PutProject.Response response) {
        //미사용 메서드
        return null;
    }
}
