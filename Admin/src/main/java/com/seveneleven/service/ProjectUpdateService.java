package com.seveneleven.service;

import com.seveneleven.common.CheckProjectValidity;
import com.seveneleven.dto.PutProject;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectType;
import com.seveneleven.exception.CompanyNotFoundException;
import com.seveneleven.exception.ProjectNotFoundException;
import com.seveneleven.repository.*;
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
        Project updatedProject = request.updateProject(project, customer, developer, projectType, bnsManager);
        projectHistoryService.saveProjectHistory(updatedProject);
        return responseConverter.toDTO(updatedProject);
    }

    @Transactional
    public void deleteProject(Long id) {
        projectHistoryService.saveProjectHistory(projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new));
        projectRepository.deleteById(id);
    }
}
