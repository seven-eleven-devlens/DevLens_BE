package com.seveneleven.project.service.authorization;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.dto.PostProjectAuthorization;
import com.seveneleven.project.repository.ProjectAuthorizationRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationStoreImpl implements AuthorizationStore {

    private final ProjectAuthorizationRepository projectAuthorizationRepository;
    private final MemberRepository memberRepository;

    @Override
    public PostProjectAuthorization.Response store(
            PostProjectAuthorization.Request requestDto,
            List<ProjectAuthorization> projectAuthorizations,
            ProjectStep step
    ) {
        PostProjectAuthorization.Response responseDto = PostProjectAuthorization.Response.create(step.getId());
        forRequestDto(responseDto, requestDto, projectAuthorizations, step);
        return responseDto;
    }

    private void forRequestDto(PostProjectAuthorization.Response responseDto,
                               PostProjectAuthorization.Request requestDto,
                               List<ProjectAuthorization> projectAuthorizations,
                               ProjectStep step
    ) {
        for (PostProjectAuthorization.MemberAuthorization request : requestDto.getAuthorizations()) {
            try {
                forProjectAuthorizationDto(request, projectAuthorizations, step);
            } catch (BusinessException e) {
                responseDto.getFailList().add(
                        PostProjectAuthorization.FailList.toDto(
                                request,
                                e.getErrorCode().getStatus(),
                                e.getMessage())
                );
            }
        }
    }

    private void forProjectAuthorizationDto(PostProjectAuthorization.MemberAuthorization request,
                                            List<ProjectAuthorization> projectAuthorizationList,
                                            ProjectStep step
    ) {
        Member member = memberRepository.findByIdAndStatus(request.getMemberId(), MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        for(ProjectAuthorization projectAuthorization : projectAuthorizationList) {
            if(projectAuthorization.getMember().getId().equals(projectAuthorization.getMember().getId())) {
                businessLogic(request, projectAuthorization);
                return;
            }
        }
        projectAuthorizationRepository.save(request.toEntity(step, member));
    }

    private void businessLogic(
            PostProjectAuthorization.MemberAuthorization request,
            ProjectAuthorization projectAuthorization
    ) {
        if(request.getProjectAuthorization().equals(projectAuthorization.getAuthorizationCode())) {
            return;
        }

        projectAuthorization.edit(request.getMemberDivision(), request.getProjectAuthorization());
        projectAuthorizationRepository.save(projectAuthorization);
    }
}
