package com.seveneleven.service;

import com.seveneleven.common.CheckProjectValidity;
import com.seveneleven.dto.GetProject;
import com.seveneleven.dto.PaginatedResponse;
import com.seveneleven.dto.PostProject;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.seveneleven.common.PageSize.DEFAULT_PAGE_SIZE;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminProjectService {
    private final AdminProjectRepository projectRepository;
    private final ProjectResponseConverter projectResponseConverter;
    private final PostProjectRequestConverter postProjectRequestConverter;
    private final PostProjectResponseConverter postProjectResponseConverter;
    private final CheckProjectValidity checkProjectValidity;
    private final AdminProjectHistoryService adminProjectHistoryService;
    private final CompanyRepository companyRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminProjectTypeRepository adminProjectTypeRepository;
    private final PutProjectResponseConverter responseConverter;

    public PostProject.Response createProject(PostProject.Request request) {
        checkProjectValidity.checkProjectDuplicatedName(request.getProjectName());
        Project project = projectRepository.save(postProjectRequestConverter.toEntity(request));
        //이력 저장
        adminProjectHistoryService.saveProjectHistory(project);
        return postProjectResponseConverter.toDTO(project);
    }

    @Transactional(readOnly = true)
    public GetProject.Response getProject(Long id) {
        return projectRepository.findById(id)
                .map(projectResponseConverter::toDTO)
                .orElseThrow(ProjectNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<GetProject.Response> getListOfProject(Integer page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("id").descending());
        Page<Project> projects = projectRepository.findAll(pageable);
        if (projects.getContent().isEmpty()) {
            throw new ProjectNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(projects.map(projectResponseConverter::toDTO));
    }

    @Transactional
    public PutProject.Response updateProject(Long id, PutProject.Request request) {
        Company customer = companyRepository.findById(request.getCustomerId()).orElseThrow(CompanyNotFoundException::new);
        Company developer = companyRepository.findById(request.getDeveloperId()).orElseThrow(CompanyNotFoundException::new);
        Member bnsManager = adminMemberRepository.findById(request.getBnsManagerId()).orElseThrow(() -> new EntityNotFoundException("bns 담당자를 찾을 수 없습니다."));
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        ProjectType projectType = adminProjectTypeRepository.findById(request.getProjectTypeId()).orElseThrow(() -> new EntityNotFoundException("프로젝트 타입을 찾을 수 없습니다"));
        //현재 프로젝트 명과 다르면 중복 체크
        if (!project.getProjectName().equals(request.getProjectName())) {
            checkProjectValidity.checkProjectDuplicatedName(request.getProjectName());
        }

        Project updatedProject = request.updateProject(project, customer, developer, projectType, bnsManager);
        adminProjectHistoryService.saveProjectHistory(updatedProject);
        return responseConverter.toDTO(updatedProject);
    }

    @Transactional
    public void deleteProject(Long id) {
        adminProjectHistoryService.saveProjectHistory(projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new));
        projectRepository.deleteById(id);
    }
}