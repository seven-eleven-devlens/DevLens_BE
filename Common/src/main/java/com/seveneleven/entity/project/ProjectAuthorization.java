package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
import com.seveneleven.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
    private memberType memberType; // 회원 구분 (client, developer)

    private String authorizationCode; // 권한 코드

    @Convert(converter = YesNoConverter.class)
    private YesNo isDeleted; // 삭제 여부

    @Getter
    @AllArgsConstructor
    enum memberType {
        CLIENT("client"),
        DEVELOPER("developer");

        final String description;
    }
}