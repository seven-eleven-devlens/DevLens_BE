package com.seveneleven.board.dto;

import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.board.constant.TaskPriority;
import com.seveneleven.util.file.dto.FileMetadataResponse;
import com.seveneleven.util.file.dto.LinkResponse;
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
    private TaskPriority priority;
    private PostStatus status;
    private String title;
    private String content;
    private LocalDate deadline;
    private String writer;
    private Boolean isAuthor;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    @Setter
    private List<GetCommentResponse> comments;
    @Setter
    private List<LinkResponse> links;
    @Setter
    private List<FileMetadataResponse> files;

    @Override
    public String toString() {
        return "PostResponse{" +
                "postId=" + postId +
                '}';
    }

    private PostResponse(
            Post post,
            Long parentPostId,
            String writer,
            Boolean isAuthor,
            List<GetCommentResponse> comments,
            List<LinkResponse> links,
            List<FileMetadataResponse> files
    ) {
        this.postId = post.getId();
        this.projectStepId = post.getProjectStep().getId();
        this.parentPostId = parentPostId;
        this.priority = post.getPriority();
        this.status = post.getStatus();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.deadline = post.getDeadline();
        this.writer = writer;
        this.isAuthor = isAuthor;
        this.createDate = post.getCreatedAt();
        this.updateDate = post.getUpdatedAt();
        this.comments = comments;
        this.links = links;
        this.files = files;
    }

    public static PostResponse getPostResponse(Post post,
                                               Long parentPostId,
                                               String writer,
                                               Boolean isAuthor,
                                               List<GetCommentResponse> comments,
                                               List<LinkResponse> links,
                                               List<FileMetadataResponse> files) {
        return new PostResponse(
                post,
                parentPostId,
                writer,
                isAuthor,
                comments,
                links,
                files
        );
    }


}
