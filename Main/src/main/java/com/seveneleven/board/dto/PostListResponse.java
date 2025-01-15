package com.seveneleven.board.dto;

import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.constant.PostStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Slf4j
@NoArgsConstructor
public class PostListResponse {

    private Long projectStepId;
    private Long parentPostId;
    private Long postId;
    private PostStatus status;
    private Integer priority;
    private String title;
    private String writer;              // 작성자
    private LocalDateTime createDate;   // 작성일자
    private LocalDate deadline;         // 마감일자

    private PostListResponse(Post post, String writer) {
        log.info("PostListResponse: post = {} : start", post.getId());
        this.projectStepId = post.getProjectStep().getId();
        this.parentPostId = post.getParentPost() != null ? post.getParentPost().getId() : null;
        this.postId = post.getId();
        this.status = post.getStatus();
        this.priority = post.getPriority();
        this.title = post.getTitle();
        this.writer = writer;
        this.createDate = post.getCreatedAt();
        this.deadline = post.getDeadline();
        log.info("PostListResponse: post = {} : end", post.getId());
    }

    public static PostListResponse toDto(Post post, String writer) {
        return new PostListResponse(post, writer);
    }

}
