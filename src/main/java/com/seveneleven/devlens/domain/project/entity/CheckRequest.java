package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import com.seveneleven.devlens.global.entity.YesNo;
import com.seveneleven.devlens.global.entity.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_request")
public class CheckRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체크 요청 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Checklist checklist; // 체크 리스트 ID

    @Column(nullable = false)
    private LocalDateTime requestDate; // 체크 요청 일시

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus; // 승인 여부(결과)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Member requester; // 요청자 ID

    private String requestIp; // 요청자 IP

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description; // 요청 내용

    public CheckRequest(Checklist checklist, LocalDateTime requestDate, ApprovalStatus approvalStatus, Member requester, String requestIp, String title, String description) {
        this.checklist = checklist;
        this.requestDate = requestDate;
        this.approvalStatus = approvalStatus;
        this.requester = requester;
        this.requestIp = requestIp;
        this.title = title;
        this.description = description;
    }
}