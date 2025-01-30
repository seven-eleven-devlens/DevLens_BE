package com.seveneleven.board.dto;

import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.board.constant.TaskPriority;
import com.seveneleven.entity.global.YesNo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long postId;
    private Long projectStepId;
    private Long parentPostId;
    private YesNo isPinnedPost;
    private TaskPriority priority;
    private PostStatus status;
    private String title;
    private String content;
    private LocalDate deadline;
    private String writer;              // 작성자
    private LocalDateTime createDate;   // 작성일자
    private LocalDateTime updateDate;   // 최근수정일자
    @Setter
    private List<GetCommentResponse> comments;

    private PostResponse(
            Post post,
            Long parentPostId,
            String writer,
            List<GetCommentResponse> comments
    ) {
        this.postId = post.getId();
        this.projectStepId = post.getProjectStep().getId();
        this.parentPostId = parentPostId;
        this.isPinnedPost = post.getIsPinnedPost();
        this.priority = post.getPriority();
        this.status = post.getStatus();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.deadline = post.getDeadline();
        this.writer = writer;
        this.createDate = post.getCreatedAt();
        this.updateDate = post.getUpdatedAt();
        this.comments = comments;
    }

    public static PostResponse getPostResponse(Post post, Long parentPostId, String writer, List<GetCommentResponse> comments) {
        return new PostResponse(
                post,
                parentPostId,
                writer,
                comments
        );
    }


}
