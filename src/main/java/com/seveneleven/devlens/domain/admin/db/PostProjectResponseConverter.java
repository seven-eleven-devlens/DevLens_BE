package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.common.EntityConverter;
import com.seveneleven.devlens.domain.admin.dto.PostProject;
import com.seveneleven.devlens.domain.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostProjectResponseConverter implements EntityConverter<PostProject.Response, Project> {
    @Override
    public PostProject.Response toDTO(Project project) {
        return new PostProject.Response(
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
                project.getPlannedEndDate()
        );
    }

    @Override
    public Project toEntity(PostProject.Response response) {
        //사용하지 않는 메서드
        return null;
    }
}
