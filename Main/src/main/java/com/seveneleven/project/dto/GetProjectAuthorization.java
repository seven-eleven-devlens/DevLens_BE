package com.seveneleven.project.dto;

import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetProjectAuthorization {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectId;
        private List<MemberAuthorization> memberAuthorization;

        @Override
        public String toString() {
            return "Response{" +
                    "projectId=" + projectId +
                    '}';
        }

        private Response(Long projectId, List<ProjectAuthorization> projectAuthorizations) {
            this.projectId = projectId;
            memberAuthorization = projectAuthorizations.stream().map(MemberAuthorization::toDto).toList();
        }

        public static Response toDto(Long projectId, List<ProjectAuthorization> projectAuthorizations) {
            return new Response(projectId, projectAuthorizations);
        }
    }

    @Getter
    public static class MemberAuthorization {
        private Long memberId;
        private String projectAuthorization;
        private MemberType memberDivision;

        @Override
        public String toString() {
            return "MemberAuthorization{" +
                    "memberId=" + memberId +
                    '}';
        }

        private MemberAuthorization(ProjectAuthorization projectAuthorization) {
            this.memberId = projectAuthorization.getMember().getId();
            this.projectAuthorization = projectAuthorization.getAuthorizationCode();
            this.memberDivision = projectAuthorization.getMemberType();
        }

        public static MemberAuthorization toDto(ProjectAuthorization projectAuthorization) {
            return new MemberAuthorization(projectAuthorization);
        }
    }
}
