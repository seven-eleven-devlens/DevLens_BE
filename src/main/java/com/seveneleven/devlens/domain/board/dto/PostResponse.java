package com.seveneleven.devlens.domain.board.dto;

import com.seveneleven.devlens.global.entity.YesNo;
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

    // private List<comment> comments;


    public PostResponse(
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

}
