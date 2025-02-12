package com.seveneleven.board.dto;

import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.board.constant.TaskPriority;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostListResponse {

    private Long projectStepId;
    private Long parentPostId;
    private Long postId;
    private PostStatus status;
    private TaskPriority priority;
    private String title;
    private String writer;
    private LocalDateTime createDate;
    private LocalDate deadline;
    private Long commentCount;

    private PostListResponse(Post post, Long commentCount) {
        this.projectStepId = post.getProjectStep().getId();
        this.parentPostId = getParentPostId();
        this.postId = post.getId();
        this.status = post.getStatus();
        this.priority = post.getPriority();
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.createDate = post.getCreatedAt();
        this.deadline = post.getDeadline();
        this.commentCount = commentCount;
    }

    public static PostListResponse toDto(Post post, Long commentCount) {
        return new PostListResponse(post, commentCount);
    }

    private Long getParentPostId() {
        if(parentPostId == null) {
            return null;
        }
        return parentPostId;
    }

    @Override
    public String toString() {
        return "PostListResponse{" +
                "title='" + title + '\'' +
                '}';
    }
}
