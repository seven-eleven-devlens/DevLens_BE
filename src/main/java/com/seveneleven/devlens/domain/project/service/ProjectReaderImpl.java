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

    @Override
    public GetProjectDetail.Response getProjectDetail(Long projectId) {
        GetProjectDetail.Response response = projectRepository.getProjectDetail(projectId);
        response.setProjectStep(projectStepRepository.findStepProcessRate(projectId));
        response.setChecklistApplicationList(checkRequestRepository.findAllApplicationLists(projectId));

        return response;
    }
}
