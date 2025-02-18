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
    private TaskPriority priority;
    private PostStatus status;
    private String title;
    private String content;
    private LocalDate deadline;
    private String writer;
    private String writerImage;
    private Boolean isAuthor;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    @Setter
    private List<GetCommentDetailResponse> comments;
    @Setter
    private List<LinkResponse> links;
    @Setter
    private List<FileMetadataResponse> files;
    private Boolean isParentPost;
    private List<RelatedPostResponse> relatedPosts;

    @Override
    public String toString() {
        return "PostResponse{" +
                "postId=" + postId +
                '}';
    }

    private PostResponse(
            Post post,
            String writer,
            String writerImage,
            Boolean isAuthor,
            List<GetCommentDetailResponse> comments,
            List<LinkResponse> links,
            List<FileMetadataResponse> files,
            Boolean isParentPost,
            List<RelatedPostResponse> relatedPosts
    ) {
        this.postId = post.getId();
        this.projectStepId = post.getProjectStep().getId();
        this.priority = post.getPriority();
        this.status = post.getStatus();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.deadline = post.getDeadline();
        this.writer = writer;
        this.writerImage = writerImage;
        this.isAuthor = isAuthor;
        this.createDate = post.getCreatedAt();
        this.updateDate = post.getUpdatedAt();
        this.comments = comments;
        this.links = links;
        this.files = files;
        this.isParentPost = isParentPost;
        this.relatedPosts = relatedPosts;

    }

    public static PostResponse getPostResponse(Post post,
                                               String writer,
                                               String writerImage,
                                               Boolean isAuthor,
                                               List<GetCommentDetailResponse> comments,
                                               List<LinkResponse> links,
                                               List<FileMetadataResponse> files,
                                               Boolean isParentPost,
                                               List<RelatedPostResponse> relatedPosts
    ) {
        return new PostResponse(
                post,
                writer,
                writerImage,
                isAuthor,
                comments,
                links,
                files,
                isParentPost,
                relatedPosts
        );
    }
}
