package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.ProjectTag;
import com.seveneleven.entity.project.ProjectType;
import com.seveneleven.entity.project.constant.MemberType;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PutProject {

    @Getter
    public static class Request {
        @NotBlank
        private String projectName; // 프로젝트명
        private Long customerId; // 고객사 ID (Company 엔티티의 ID)
        private Long developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private ProjectStatusCode projectStatusCode; //
        private String bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
        private LocalDateTime finalApprovalDate; // 최종 결재일시
        private List<String> projectTags;
        private List<PatchAuthorization.CustomerMemberAuthorization> customerAuthorizations;
        private List<PatchAuthorization.DeveloperMemberAuthorization> developerAuthorizations;

        @Override
        public String toString() {
            return "Request{" +
                    "projectName='" + projectName + '\'' +
                    '}';
        }

        public Project updateProject(
                Project project,
                ProjectType projectType
        ) {
            return project.update(
                    projectName,
                    projectDescription,
                    projectStatusCode,
                    plannedStartDate,
                    plannedEndDate,
                    startDate,
                    endDate,
                    finalApprovalDate,
                    projectType,
                    bnsManager
            );
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private String projectName; // 프로젝트명
        private String customerId; // 고객사 ID (Company 엔티티의 ID)
        private String developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private String projectType; // 프로젝트 유형 ID
        private ProjectStatusCode projectStatusCode; //
        private String bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
        private LocalDateTime finalApprovalDate; // 최종 결재일시
        private List<String> projectTags;
        private List<PatchAuthorization.CustomerMemberAuthorization> customerMemberAuthorizations;
        private List<PatchAuthorization.DeveloperMemberAuthorization> developerMemberAuthorizations;

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    '}';
        }

        private Response(Project project, List<ProjectTag> tags, List<ProjectAuthorization> authorizations) {
            id = project.getId();
            projectName = project.getProjectName();
            customerId = project.getCustomer().getCompanyName();
            developerId = project.getDeveloper().getCompanyName();
            projectDescription = project.getProjectDescription();
            projectType = project.getProjectType().getProjectTypeName();
            bnsManager = project.getBnsManager();
            plannedStartDate = project.getPlannedStartDate();
            plannedEndDate = project.getPlannedEndDate();
            startDate = project.getStartDate(); // 시작일
            endDate = project.getEndDate(); //종료일
            finalApprovalDate = project.getFinalApprovalDate(); // 최종 결재일시
            projectTags = tags.stream().map(ProjectTag::getTag).toList();
            this.customerMemberAuthorizations = new ArrayList<>();
            this.developerMemberAuthorizations = new ArrayList<>();
            authorizations.forEach(authorization -> {
                if(MemberType.CLIENT.equals(authorization.getMemberType())) {
                    customerMemberAuthorizations.add(new PatchAuthorization.CustomerMemberAuthorization(authorization));
                } else {
                    developerMemberAuthorizations.add(new PatchAuthorization.DeveloperMemberAuthorization(authorization));
                }
            });
        }

        public static Response of(Project project, List<ProjectTag> projectTags, List<ProjectAuthorization> authorizations) {
            return new Response(project, projectTags, authorizations);
        }
    }
}
