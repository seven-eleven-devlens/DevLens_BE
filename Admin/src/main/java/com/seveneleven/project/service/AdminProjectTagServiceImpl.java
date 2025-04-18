package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProjectTagServiceImpl implements AdminProjectTagService {

    private final ProjectTagReader projectTagReader;
    private final AdminProjectTagStore projectTagStore;

    @Override
    public List<ProjectTag> getAllProjectTags(Long projectId) {
        return projectTagReader.readByProjectId(projectId);
    }

    @Override
    public List<ProjectTag> storeProjectTags(Project project, List<String> tags) {
        List<ProjectTag> projectTags = projectTagReader.readByProjectId(project.getId());
        return projectTagStore.store(project, tags, projectTags);
    }
}
