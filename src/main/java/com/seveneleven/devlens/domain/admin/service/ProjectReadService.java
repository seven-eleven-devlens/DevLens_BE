package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.db.ProjectRepository;
import com.seveneleven.devlens.domain.admin.db.ProjectResponseConverter;
import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.dto.ProjectDto;
import com.seveneleven.devlens.domain.admin.exception.ProjectNotFoundException;
import com.seveneleven.devlens.domain.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectReadService {
    private final ProjectRepository projectRepository;
    private final ProjectResponseConverter projectResponseConverter;
    private final int pageSize = 10;
    public ProjectDto.ProjectResponse getProject(
            Long id
    ){
        return projectRepository.findById(id)
                .map(projectResponseConverter::toDTO)
                .orElseThrow(ProjectNotFoundException::new);
    }

    public PaginatedResponse<ProjectDto.ProjectResponse> getListOfProject(
            Integer page
    ){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("Id").descending());
        Page<Project> projects = projectRepository.findAll(pageable);
        if(projects.getContent().isEmpty()){
            throw new ProjectNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(projects.map(projectResponseConverter::toDTO));
    }
}