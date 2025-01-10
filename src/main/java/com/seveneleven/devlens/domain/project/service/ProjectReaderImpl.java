package com.seveneleven.devlens.domain.project.service;

import com.seveneleven.devlens.domain.project.dto.GetProjectDetail;
import com.seveneleven.devlens.domain.project.dto.GetProjectList;
import com.seveneleven.devlens.domain.project.repository.CheckRequestRepository;
import com.seveneleven.devlens.domain.project.repository.ProjectRepository;
import com.seveneleven.devlens.domain.project.repository.ProjectStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProjectReaderImpl implements ProjectReader {
    private final ProjectRepository projectRepository;
    private final ProjectStepRepository projectStepRepository;
    private final CheckRequestRepository checkRequestRepository;

    @Override
    @Transactional(readOnly = true)
    public GetProjectList.Response getProjectList(Long memberId, Long companyId) {
        return new GetProjectList.Response(
                GetProjectList.GetMyProjectResponseInfo.toDto(projectRepository.findAllProgressingProjects(memberId)),
                GetProjectList.GetCompanyProjectResponseInfo.toDto(projectRepository.findAllCompanyProgressingProjects(companyId))
        );
    }

    @Override
    public GetProjectDetail.Response getProjectDetail(Long projectId) {
        return new GetProjectDetail.Response(
                projectRepository.getProjectDetail(projectId),
                projectStepRepository.findStepProcessRate(projectId),
                checkRequestRepository.findAllApplicationLists(projectId)
        );
    }
}
