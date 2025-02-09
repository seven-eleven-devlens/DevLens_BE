package com.seveneleven.board.dto;

import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.global.YesNo;
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
    private YesNo isEdited;

    private GetCommentResponse(Comment comment, YesNo isEdited) {
        this.commentId = comment.getId();
        this.parentCommentId = getParentCommentId(comment.getParentComment());
        this.registerId = comment.getCreatedBy();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
        this.createdAt = getCreatedAt(comment);
        this.isEdited = isEdited;
    }

    @Override
    public String toString() {
        return "GetCommentResponse{" +
                "commentId=" + commentId +
                '}';
    }

    // Comment 를 GetCommentResponse 로 변환
    public static GetCommentResponse toDto(Comment comment, YesNo isEdited) {
        return new GetCommentResponse(comment, isEdited);
    }

    private static LocalDateTime getCreatedAt(Comment comment) {
        if(comment.getModifierIp() != null) {
            return comment.getUpdatedAt();
        }
        return comment.getCreatedAt();
    }

    private static Long getParentCommentId(Comment parentComment) {
        if(parentComment != null) {
            return parentComment.getId();
        }
        return null;
    }
}
