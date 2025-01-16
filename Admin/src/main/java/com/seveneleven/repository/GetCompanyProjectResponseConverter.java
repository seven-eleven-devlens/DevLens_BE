package com.seveneleven.repository;

import com.seveneleven.common.EntityConverter;
import com.seveneleven.dto.GetCompanyProject;
import com.seveneleven.entity.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetCompanyProjectResponseConverter implements EntityConverter<GetCompanyProject, Project> {
    @Override
    public GetCompanyProject toDTO(Project project) {
        return GetCompanyProject.of(project);
    }

    @Override
    public Project toEntity(GetCompanyProject response) {
        //사용하지 않는 메서드
        return null;
    }
}
