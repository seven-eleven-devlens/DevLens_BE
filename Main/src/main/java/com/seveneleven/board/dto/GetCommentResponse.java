package com.seveneleven.board.dto;

import com.seveneleven.entity.board.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCommentResponse {
    private Long commentId;
    private Long parentCommentId;
    private Long registerId;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private GetCommentResponse(Comment comment, String writer) {
        this.commentId = comment.getId();
        this.parentCommentId = getParentCommentId(comment.getParentCommentId());
        this.registerId = comment.getCreatedBy();
        this.writer = writer;
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

    // Comment 를 GetCommentResponse 로 변환
    public static GetCommentResponse toDto(Comment comment, String writer) {
        return new GetCommentResponse(comment, writer);
    }

    private static Long getParentCommentId(Comment parentCommentId) {
        if(parentCommentId != null) {
            return parentCommentId.getId();
        }
        return null;
    }
}
