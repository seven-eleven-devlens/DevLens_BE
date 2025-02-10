package com.seveneleven.project.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class PostProjectAuthorization {

    @Getter
    @NoArgsConstructor
    public static class Request {
        List<MemberAuthorization> authorizations;

        @Override
        public String toString() {
            return "Request{" +
                    "authorizations=" + authorizations +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberAuthorization {
        private Long memberId;
        private String projectAuthorization;
        private MemberType memberDivision;

        public ProjectAuthorization toEntity(Project project, Member member) {
            return ProjectAuthorization.create(member, project, memberDivision, projectAuthorization);
        }

        @Override
        public String toString() {
            return "MemberAuthorization{" +
                    "memberId=" + memberId +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long stepId;
        private List<FailList> failList;

        private Response(Long stepId) {
            this.stepId = stepId;
            this.failList = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Response{" +
                    "stepId=" + stepId +
                    '}';
        }

        public static Response create(Long stepId) {
            return new Response(stepId);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class FailList {
        private Long memberId;
        private MemberType memberDivision;
        private String projectAuthorization;
        private HttpStatus status;
        private String message;

        private FailList(MemberAuthorization member, HttpStatus status, String message) {
            this.memberId = member.getMemberId();
            this.memberDivision = member.getMemberDivision();
            this.projectAuthorization = member.getProjectAuthorization();
            this.status = status;
            this.message = message;
        }

        @Override
        public String toString() {
            return "FailList{" +
                    "memberId=" + memberId +
                    '}';
        }

        public static FailList toDto(MemberAuthorization member, HttpStatus status, String message) {
            return new FailList(member, status, message);
        }
    }
}
