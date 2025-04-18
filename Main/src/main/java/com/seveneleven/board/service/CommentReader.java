package com.seveneleven.board.service;

import com.seveneleven.board.dto.GetCommentResponse;
import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.member.Member;

import java.util.List;

public interface CommentReader {
    List<Comment> getComments(Long postId);

    Comment getLatestComment(Long postId);

    List<GetCommentResponse> getIsActiveComments(Long postId, Long userId);

    Comment getComment(Long commentId);

    Long getMaxRef();

    Member getMember(Long userId);
}
