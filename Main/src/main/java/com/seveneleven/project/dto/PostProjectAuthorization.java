package com.seveneleven.project.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class PostProjectAuthorization {

    @Getter
    public static class Request {
        List<MemberAuthorization> authorizations;
    }

    @Getter
    public static class MemberAuthorization {
        Long memberId;
        String projectAuthorization;
        MemberType memberDivision;

        public ProjectAuthorization toEntity(ProjectStep projectStep, Member member) {
            return ProjectAuthorization.create(member, projectStep, memberDivision, projectAuthorization);
        }
    }

    @Getter
    public static class Response {
        Long stepId;
        List<FailList> failList;

        private Response(Long stepId) {
            this.stepId = stepId;
            this.failList = new ArrayList<>();
        }

        public static Response create(Long stepId) {
            return new Response(stepId);
        }
    }

    @Getter
    public static class FailList {
        Long memberId;
        MemberType memberDivision;
        String projectAuthorization;
        HttpStatus status;
        String message;

        private FailList(MemberAuthorization member, HttpStatus status, String message) {
            this.memberId = member.getMemberId();
            this.memberDivision = member.getMemberDivision();
            this.projectAuthorization = member.getProjectAuthorization();
            this.status = status;
            this.message = message;
        }

        public static FailList toDto(MemberAuthorization member, HttpStatus status, String message) {
            return new FailList(member, status, message);
        }


    }
}
