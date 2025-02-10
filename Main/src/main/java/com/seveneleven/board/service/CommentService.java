package com.seveneleven.board.service;

import com.seveneleven.board.dto.GetCommentResponse;
import com.seveneleven.board.dto.PatchCommentRequest;
import com.seveneleven.board.dto.PostCommentRequest;
import com.seveneleven.entity.board.Post;
import com.seveneleven.util.security.dto.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {

    @Transactional(readOnly = true)
    List<GetCommentResponse> selectCommentList(Long postId);

    @Transactional
    void createComment(Long postId, PostCommentRequest postCommentRequest, HttpServletRequest request, String registerName);

    @Transactional
    void updateComment(Long postId, Long commentId, PatchCommentRequest patchCommentRequest, HttpServletRequest request, CustomUserDetails customUserDetails);

    @Transactional
    void deleteComment(Long postId, Long commentId, HttpServletRequest request, CustomUserDetails customUserDetails);

    @Transactional
    void deleteAllComments(Post post, String deleterIp);
}
