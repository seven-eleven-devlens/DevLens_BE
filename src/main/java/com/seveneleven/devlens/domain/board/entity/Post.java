package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.domain.board.dto.PostStatus;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends BaseEntity {

    /*
        id : 게시물 ID (문서번호)
        projectStepId : 프로젝트 단계 ID
        parentPostId : 부모 게시물 ID
        isPinnedPost : 상단고정 여부 (Y, N)
        priority : 우선순위 (1,2,3)
        status : 상태 (DEFAULT, IN_PROGRESS, ADDITION, COMPLETED, ON_HOLD)
        title : 제목
        content : 내용
        hasFile : 파일 유무 (Y, N)
        hasLink : 링크 유무 (Y, N)
        deadline : 마감일자
        registerIp : 등록자 IP
        modifierIp : 수정자 IP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "project_step_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectStep projectStepId;

    @JoinColumn(name = "parent_post_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post parentPostId;

    @Column(name = "is_pinned_post")
    private YN isPinnedPost;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "status", nullable = false, length = 50)
    private PostStatus status;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "has_file", nullable = false)
    private YN hasFile = YN.N;

    @Column(name = "has_link", nullable = false)
    private YN hasLink = YN.N;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "register_ip", length = 50)
    private String registerIp;

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp;

    // 게시글 생성
    public static Post createPost(
            ProjectStep projectStepId,
            Post parentPostId,
            YN isPinnedPost,
            Integer priority,
            PostStatus status,
            String title,
            String content,
            LocalDate deadline,
            String registerIp,
            String modifierIp
    ) {
        Post post = new Post();
        post.projectStepId = projectStepId;
        post.parentPostId = parentPostId;
        post.isPinnedPost = isPinnedPost;
        post.priority = priority;
        post.status = status;
        post.title = title;
        post.content = content;
        post.deadline = deadline;
        post.registerIp = registerIp;
        post.modifierIp = modifierIp;
        return post;
    }

    // 게시글 수정
    public void updatePost(
            YN isPinnedPost,
            Integer priority,
            PostStatus status,
            String title,
            String content,
            LocalDate deadline,
            String modifierIp
    ) {
        this.isPinnedPost = isPinnedPost;
        this.priority = priority;
        this.status = status;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.modifierIp = modifierIp;
    }

    // 파일 존재 여부 변경 메서드
    public void changeHasFile(YN hasFile) {
        this.hasFile = YN.Y;
    }

    // 링크 존재 여부 변경 메서드
    public void changeHasLink(YN hasLink) {
        this.hasLink = YN.Y;
    }

}