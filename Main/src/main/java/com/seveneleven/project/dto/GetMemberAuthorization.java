package com.seveneleven.project.dto;

import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GetMemberAuthorization {

    @Getter
    @NoArgsConstructor
    public static class Response {
        Long memberId;
        String authorization;
        MemberType memberType;

        private Response(ProjectAuthorization authorization) {
            this.memberId = authorization.getMember().getId();
            this.authorization = authorization.getAuthorizationCode();
            this.memberType = authorization.getMemberType();
        }

        public static Response toDto(ProjectAuthorization authorization) {
            return new Response(authorization);
        }
    }
}
