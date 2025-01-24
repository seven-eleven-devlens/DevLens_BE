package com.seveneleven.project.service;

import com.seveneleven.member.service.MemberServiceImpl;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.service.dashboard.ProjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectReader projectReader;
    private final MemberServiceImpl memberService;

    @Override
    public GetProjectList.Response getProjectList(Long memberId) {
        // TODO - securityUtils 사용 확인
        // TODO - MemberRepository -> Reader로 변경 필요
        return projectReader.getProjectList(memberId, memberService.getCompanyIdById(memberId));
    }

//    @Override
//    public GetProjectDetail.Response getProjectDetail(Long projectId) {
//        return projectReader.getProjectDetail(projectId);
//    }
}
