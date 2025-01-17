package com.seveneleven.board.dto;

import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.constant.PostStatus;
import com.seveneleven.entity.global.YesNo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long postId;
    private Long projectStepId;
    private Long parentPostId;
    private YesNo isPinnedPost;
    private Integer priority;
    private PostStatus status;
    private String title;
    private String content;
    private LocalDate deadline;
    private String writer;              // 작성자
    private LocalDateTime createDate;   // 작성일자
    private LocalDateTime updateDate;   // 최근수정일자

    // todo: 댓글 리스트 추가 예정
    // private List<comment> comments;


    private PostResponse(
            Long postId,
            Long projectStepId,
            Long parentPostId,
            YesNo isPinnedPost,
            Integer priority,
            PostStatus status,
            String title,
            String content,
            LocalDate deadline,
            String writer,
            LocalDateTime createDate,
            LocalDateTime updateDate
    ) {
        this.postId = postId;
        this.projectStepId = projectStepId;
        this.parentPostId = parentPostId;
        this.isPinnedPost = isPinnedPost;
        this.priority = priority;
        this.status = status;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.writer = writer;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static PostResponse getPostResponse(Post post, Long parentPostId, String memberName) {
        return new PostResponse(
                post.getId(),
                post.getProjectStep().getId(),
                parentPostId,           // 부모게시물이 없는 경우 null 반환
                post.getIsPinnedPost(),
                post.getPriority(),
                post.getStatus(),
                post.getTitle(),
                post.getContent(),
                post.getDeadline(),
                memberName,             // memberId가 아닌 name
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }


}
