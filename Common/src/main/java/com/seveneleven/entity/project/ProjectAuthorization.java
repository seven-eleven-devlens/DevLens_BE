package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
import com.seveneleven.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_authorization")
public class ProjectAuthorization extends BaseEntity {

    @EmbeddedId
    private MemberProjectStepId id; // 복합 키

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId") // 복합 키의 memberId와 매핑
    @JoinColumn(name = "member_id", nullable = false, referencedColumnName = "id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectStepId") // 복합 키의 projectStepId와 매핑
    @JoinColumn(name = "project_step_id", nullable = false)
    private ProjectStep projectStep;

    @Enumerated(EnumType.STRING)
    private MemberType memberType; // 회원 구분 (client, developer)

    private String authorizationCode; // 권한 코드

    @Convert(converter = YesNoConverter.class)
    private YesNo isActive; // 삭제 여부

    private ProjectAuthorization(Member member, ProjectStep projectStep, MemberType memberType, String authorizationCode) {
        this.id = new MemberProjectStepId(member.getId(), projectStep.getId());
        this.member = member;
        this.projectStep = projectStep;
        this.memberType = memberType;
        this.authorizationCode = authorizationCode;
        this.isActive = YesNo.YES;
    }

    public static ProjectAuthorization create(Member member, ProjectStep projectStep, MemberType memberType, String authorizationCode) {
        return new ProjectAuthorization(member, projectStep, memberType, authorizationCode);
    }

    public void edit(MemberType memberType, String authorizationCode) {
        this.memberType = memberType;
        this.authorizationCode = authorizationCode;
    }

    public ProjectAuthorizationHistory delete() {
        isActive = YesNo.NO;
        return ProjectAuthorizationHistory.create(this);
    }
}