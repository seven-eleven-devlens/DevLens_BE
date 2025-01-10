package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.common.CheckProjectValidity;
import com.seveneleven.devlens.domain.admin.dto.PutProject;
import com.seveneleven.devlens.domain.admin.exception.CompanyNotFoundException;
import com.seveneleven.devlens.domain.admin.exception.ProjectNotFoundException;
import com.seveneleven.devlens.domain.admin.repository.*;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.project.entity.Project;
import com.seveneleven.devlens.domain.project.entity.ProjectType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectUpdateService {
    private final AdminProjectRepository projectRepository;
    private final CheckProjectValidity checkProjectValidity;
    private final CompanyRepository companyRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminProjectTypeRepository adminProjectTypeRepository;
    private final PutProjectResponseConverter responseConverter;
    private final ProjectHistoryService projectHistoryService;

    @Transactional
    public PutProject.Response updateProject(Long id, PutProject.Request request) {
        Company customer = companyRepository.findById(request.getCustomerId()).orElseThrow(CompanyNotFoundException::new);
        Company developer = companyRepository.findById(request.getDeveloperId()).orElseThrow(CompanyNotFoundException::new);
        Member bnsManager = adminMemberRepository.findById(request.getBnsManagerId()).orElseThrow(() -> new EntityNotFoundException("bns 담당자를 찾을 수 없습니다."));
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        ProjectType projectType = adminProjectTypeRepository.findById(request.getProjectTypeId()).orElseThrow(() -> new EntityNotFoundException("프로젝트 타입을 찾을 수 없습니다"));

        if (!project.getProjectName().equals(request.getProjectName())) {
            checkProjectValidity.checkProjectDuplicatedName(request.getProjectName());
        }
        Project updatedProject = project.update(request, customer, developer, projectType, bnsManager);
        projectHistoryService.saveProjectHistory(updatedProject);
        return responseConverter.toDTO(updatedProject);
    }
}
