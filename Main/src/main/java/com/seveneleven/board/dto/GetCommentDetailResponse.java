package com.seveneleven.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCommentDetailResponse {
    private Long commentId;
    private Long parentCommentId;
    private Long registerId;
    private String writer;
    private String writerImage;
    private String content;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Boolean isEdited;
    private Boolean isAuthor;

    public GetCommentDetailResponse(GetCommentResponse response, String writerImage) {
        this.commentId = response.getCommentId();
        this.parentCommentId = response.getParentCommentId();
        this.registerId = response.getRegisterId();
        this.writer = response.getWriter();
        this.content = response.getContent();
        this.createdBy = response.getCreatedBy();
        this.writerImage = writerImage;
        this.createdAt = response.getCreatedAt();
        this.isEdited = response.getIsEdited();
        this.isAuthor = response.getIsAuthor();
    }

    @Override
    public String toString() {
        return "GetCommentDetailResponse{" +
                "commentId=" + commentId +
                '}';
    }
}
