package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.PostProject;
import com.seveneleven.entity.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostProjectResponseConverter implements EntityConverter<PostProject.Response, Project> {
    @Override
    public PostProject.Response toDTO(Project project) {
        return PostProject.Response.of(project);
    }

    @Override
    public Project toEntity(PostProject.Response response) {
        //사용하지 않는 메서드
        return null;
    }
}
