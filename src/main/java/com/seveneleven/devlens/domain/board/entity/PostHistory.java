package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.domain.board.dto.PostAction;
import com.seveneleven.devlens.domain.board.dto.PostStatus;
import com.seveneleven.devlens.domain.board.dto.PostUpdateRequest;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_history")
public class PostHistory {

        /*
        id : 게시물 이력 ID
        projectStepId : 프로젝트 단계 ID
        parentPostId : 부모 게시물 ID
        postId : 게시물 ID
        isPinnedPost : 상단고정 여부 (Y, N)
        priority : 우선순위 (1,2,3)
        status : 상태 (DEFAULT, IN_PROGRESS, ADDITION, COMPLETED, ON_HOLD)
        title : 제목
        content : 내용
        hasFile : 파일 유무
        hasLink : 링크 유무
        deadline : 마감일자
        action : 작업 종류 (CREATE, UPDATE, DELETE)
        registerIp : 등록자 IP
        modifierIp : 수정자 IP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_step_id", nullable = false)
    private Long projectStepId;

    @Column(name = "parent_post_id")
    private Long parentPostId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "is_pinned_post")
    @Enumerated(EnumType.STRING)
    private YN isPinnedPost;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PostStatus status; // 게시물 상태 종류(DEFAULT, IN_PROGRESS, ADDITION, COMPLETED, ON_HOLD

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "has_file", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN hasFile;

    @Column(name = "has_link", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN hasLink;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostAction action; // 작업 종류 (CREATE, UPDATE, DELETE)

    @Column(name = "registered_ip", length = 50)
    private String registeredIp; // 등록자 IP

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP

    // 게시글 이력 생성
    public static PostHistory createPostHistory(Post post, PostAction action) {
        PostHistory postHistory = new PostHistory();
        postHistory.projectStepId = post.getProjectStepId().getId();
        postHistory.parentPostId = (post.getParentPostId() != null ? post.getParentPostId().getId() : null);
        postHistory.postId = post.getId();
        postHistory.isPinnedPost = post.getIsPinnedPost();
        postHistory.priority = post.getPriority();
        postHistory.status = post.getStatus();
        postHistory.title = post.getTitle();
        postHistory.content = post.getContent();
        postHistory.hasFile = post.getHasFile();
        postHistory.hasLink = post.getHasLink();
        postHistory.deadline = post.getDeadline();
        postHistory.action = action;
        postHistory.registeredIp = postHistory.getRegisteredIp();
        postHistory.modifierIp = postHistory.getModifierIp();

        return postHistory;
    }


}
