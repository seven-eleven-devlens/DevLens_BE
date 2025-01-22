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
public class AdminProjectServiceImpl implements AdminProjectService{
    private final ProjectResponseConverter projectResponseConverter;
    private final PostProjectRequestConverter postProjectRequestConverter;
    private final PostProjectResponseConverter postProjectResponseConverter;
    private final PutProjectResponseConverter responseConverter;
    private final CheckProjectValidity checkProjectValidity;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminProjectTypeRepository adminProjectTypeRepository;
    private final AdminProjectReader adminProjectReader;
    private final AdminProjectStore adminProjectStore;
    private final AdminCompanyReader adminCompanyReader;

    public PostProject.Response createProject(PostProject.Request request) {
        //중복 확인
        checkProjectValidity.checkProjectDuplicatedName(request.getProjectName());
        return postProjectResponseConverter.toDTO(
                adminProjectStore.store(postProjectRequestConverter.toEntity(request))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public GetProject.Response getProject(Long id) {
        return projectResponseConverter.toDTO(adminProjectReader.getProject(id));
    }

    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<GetProject.Response> getListOfProject(Integer page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE.getPageSize(), Sort.by("id").descending());
        Page<Project> projects = adminProjectReader.findAll(pageable);
        if (projects.getContent().isEmpty()) throw new ProjectNotFoundException();

        return PaginatedResponse.createPaginatedResponse(projects.map(projectResponseConverter::toDTO));
    }

    @Transactional
    @Override
    public PutProject.Response updateProject(Long id, PutProject.Request request) {
        Company customer = adminCompanyReader.getCompany(request.getCustomerId());
        Company developer = adminCompanyReader.getCompany(request.getDeveloperId());
        Member bnsManager = adminMemberRepository.findById(request.getBnsManagerId()).orElseThrow(() -> new EntityNotFoundException("bns 담당자를 찾을 수 없습니다."));
        Project project = adminProjectReader.getProject(id);
        ProjectType projectType = adminProjectTypeRepository.findById(request.getProjectTypeId()).orElseThrow(() -> new EntityNotFoundException("프로젝트 타입을 찾을 수 없습니다"));
        //현재 프로젝트 명과 다르면 중복 체크
        if (!project.getProjectName().equals(request.getProjectName())) {
            checkProjectValidity.checkProjectDuplicatedName(request.getProjectName());
        }

        Project updatedProject = request.updateProject(project, customer, developer, projectType, bnsManager);
        return responseConverter.toDTO(adminProjectStore.store(updatedProject));
    }

    @Transactional
    @Override
    public void deleteProject(Long id) {
        adminProjectStore.delete(id);
    }
}