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
    private String writer;              // 작성자
    private LocalDateTime createDate;   // 작성일자
    private LocalDate deadline;         // 마감일자

    private PostListResponse(Post post, String writer) {
        this.projectStepId = post.getProjectStep().getId();
        this.parentPostId = getParentPostId();
        this.postId = post.getId();
        this.status = post.getStatus();
        this.priority = post.getPriority();
        this.title = post.getTitle();
        this.writer = writer;
        this.createDate = post.getCreatedAt();
        this.deadline = post.getDeadline();
    }

    public static PostListResponse toDto(Post post, String writer) {
        return new PostListResponse(post, writer);
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
