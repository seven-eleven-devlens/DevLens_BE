package com.seveneleven.project.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetMemberAuthorization;
import com.seveneleven.project.dto.GetProjectAuthorization;
import com.seveneleven.project.dto.PostProjectAuthorization;

public interface ProjectAuthorizationService {
    GetProjectAuthorization.Response getProjectAuthorization(Project project);
    GetMemberAuthorization.Response getMemberAuthorization(Project project, Member member);
    PostProjectAuthorization.Response createProjectAuthorization(Project project, PostProjectAuthorization.Request requestDto);
}
