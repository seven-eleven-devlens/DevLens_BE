package com.seveneleven.devlens.domain.project.service.dashboard;

import com.seveneleven.devlens.domain.project.dto.GetProjectList;
import com.seveneleven.devlens.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProjectReaderImpl implements ProjectReader {
    private final ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public GetProjectList.Response getProjectList(Long memberId, Long companyId) {
        return new GetProjectList.Response(
                projectRepository.findAllProgressingProjects(memberId)
                        .stream()
                        .map(GetProjectList.GetMyProjectResponseInfo::toDto)
                        .toList(),
                projectRepository.findAllCompanyProgressingProjects(companyId)
                        .stream()
                        .map(GetProjectList.GetCompanyProjectResponseInfo::toDto)
                        .toList()
        );
    }
}
