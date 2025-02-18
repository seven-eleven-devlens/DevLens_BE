package com.seveneleven.board.service;

import com.seveneleven.board.dto.GetCommentResponse;
import com.seveneleven.board.repository.CommentRepository;
import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.seveneleven.response.ErrorCode.NOT_FOUND_COMMENT;
import static com.seveneleven.response.ErrorCode.NOT_FOUND_MEMBER;

@Component
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Comment> getComments(Long postId) {
        return commentRepository.findCommentList(postId);
    }

    @Override
    public Comment getLatestComment(Long postId) {
        return commentRepository.findFirstByPostIdOrderByCreatedAtDesc(postId);
    }

    @Override
    public List<GetCommentResponse> getIsActiveComments(Long postId, Long userId) {
        return commentRepository.findIsActiveCommentList(postId, userId);
    }

    @Override
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
    }

    @Override
    public Long getMaxRef() {
        return commentRepository.findMaxRef();
    }


    @Override
    public Member getMember(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));
    }

}
