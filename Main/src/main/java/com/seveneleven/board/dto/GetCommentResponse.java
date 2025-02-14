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
    private Boolean isEdited;
    private Boolean isAuthor;

    private GetCommentResponse(Comment comment, LocalDateTime createdAt, Boolean isEdited, Boolean isAuthor) {
        this.commentId = comment.getId();
        this.parentCommentId = getParentCommentId(comment.getParentComment());
        this.registerId = comment.getCreatedBy();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
        this.createdAt = createdAt;
        this.isEdited = isEdited;
        this.isAuthor = isAuthor;
    }

    @Override
    public String toString() {
        return "GetCommentResponse{" +
                "commentId=" + commentId +
                '}';
    }

    // Comment 를 GetCommentResponse 로 변환
    public static GetCommentResponse toDto(Comment comment, LocalDateTime createdAt, Boolean isEdited, Boolean isAuthor) {
        return new GetCommentResponse(comment, createdAt, isEdited, isAuthor);
    }

//    private static LocalDateTime getCreatedAt(Comment comment) {
//        if(comment.getModifierIp() != null) {
//            return comment.getUpdatedAt();
//        }
//        return comment.getCreatedAt();
//    }

    private static Long getParentCommentId(Comment parentComment) {
        if(parentComment != null) {
            return parentComment.getId();
        }
        return null;
    }
}
