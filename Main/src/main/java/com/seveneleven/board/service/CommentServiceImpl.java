package com.seveneleven.board.service;

import com.seveneleven.board.dto.GetCommentResponse;
import com.seveneleven.board.dto.PatchCommentRequest;
import com.seveneleven.board.dto.PostCommentRequest;
import com.seveneleven.board.repository.CommentRepository;
import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.util.GetIpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seveneleven.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final GetIpUtil getIpUtil;

    /**
     * 함수명 : selectCommentList()
     * 함수 목적 : 댓글 목록을 조회하는 메서드
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetCommentResponse> selectCommentList(Long postId){
        // todo: 댓글 페이징 기능 고려
        Post post = getPost(postId);

        return commentRepository.getCommentList(post.getId())
                .stream()
                .map(GetCommentResponse::toDto)
                .toList();
    }

    /**
     * 함수명 : createComment()
     * 함수 목적 : 댓글을 생성하는 메서드
     */
    @Transactional
    @Override
    public void createComment(Long postId, PostCommentRequest postCommentRequest, HttpServletRequest request, Long registerId) {
        Post post = getPost(postId);
        Member member = memberRepository.findById(registerId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));

        String registerIp = getIpUtil.getIpAddress(request);

        // 댓글인 경우
        if(postCommentRequest.getParentCommentId() == null) {
            saveComment(post, null, commentRepository.findMaxRef() + 1, 0, postCommentRequest.getContent(), member.getName(), registerIp);
        }

        // 대댓글인 경우
        if(postCommentRequest.getParentCommentId() != null) {
            Comment parentComment = getComment(postCommentRequest.getParentCommentId());
            saveComment(post, parentComment, parentComment.getRef(), parentComment.getChildCommentNum() + 1, postCommentRequest.getContent(), member.getName(), registerIp);
            // 부모 댓글의 child_comment_num 값 1 증가
            increaseChildCommentNum(postCommentRequest.getParentCommentId());
        }
    }

    /**
     * 함수명 : updateComment()
     * 함수 목적 : 댓글을 수정하는 메서드
     */
    @Transactional
    @Override
    public void updateComment(Long postId, Long commentId, PatchCommentRequest patchCommentRequest, HttpServletRequest request, Long modifierId) {
        existPost(postId);
        Comment comment = getComment(commentId);

        // 작성자 일치 여부 확인
        matchCommentWriter(comment.getCreatedBy(), modifierId);

        // 기존 댓글 수정
        comment.updateComment(patchCommentRequest.getContent(), getIpUtil.getIpAddress(request));
        commentRepository.save(comment);
    }

    /**
     * 함수명 : deleteComment()
     * 함수 목적 : 댓글을 삭제하는 메서드
     */
    @Transactional
    @Override
    public void deleteComment(Long postId, Long commentId, HttpServletRequest request, Long deleterId) {
        existPost(postId);
        Comment comment = getComment(commentId);

        // 작성자 일치 여부 확인
        matchCommentWriter(comment.getCreatedBy(), deleterId);

        // 댓글인 경우
        if(comment.getParentCommentId() == null) {
            // 대댓글이 존재하는 경우, 댓글 삭제 불가능
            if(comment.getChildCommentNum() >= 1) {
                throw new BusinessException(NOT_DELETE_PARENT_COMMENT);
            }
        }

        // 대댓글인 경우
        if(comment.getParentCommentId() != null) {
            // 댓글의 child_comment_num - 1 감소
            decreaseChildCommentNum(comment.getParentCommentId().getId());
        }
        comment.deleteComment(getIpUtil.getIpAddress(request));
        commentRepository.save(comment);
    }

    /**
     * 함수명 : saveComment()
     * 함수 목적 : 댓글 또는 대댓글 구분값으로 댓글을 생성하는 메서드
     */
    private void saveComment(Post post, Comment parentComment, Long ref, Integer refOrder, String content, String writer, String ip) {
        commentRepository.save(
                Comment.createComment(
                        post,
                        parentComment,
                        ref,
                        refOrder,
                        0,
                        content,
                        writer,
                        ip
                )
        );
    }

    /**
     * 함수명 : existPost()
     * 함수 목적 : 게시글 존재 여부 확인
     */
    private void existPost(Long postId) {
        if(postRepository.findById(postId).isEmpty()) {
            throw new BusinessException(NOT_FOUND_POST);
        }
    }

    /**
     * 함수명 : getPost()
     * 함수 목적 : 게시글 정보 가져오기
     */
    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    /**
     * 함수명 : getComment()
     * 함수 목적 : 댓글 정보 가져오기
     */
    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
    }

    /**
     * 함수명 : matchCommentWriter()
     * 함수 목적 : 댓글 작성자 일치 여부 확인
     */
    private void matchCommentWriter(Long commentCreatedBy, Long modifierId) {
        if(!commentCreatedBy.equals(modifierId)) {
            throw new BusinessException(NOT_MATCH_WRITER);
        }
    }

    /**
     * 함수명 : increaseChildPostNum()
     * 함수 목적 : 답글 생성 시 child_post_num 증가
     */
    private void increaseChildCommentNum(Long parentCommentId) {
        Comment comment = commentRepository.findById(parentCommentId).orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
        comment.increaseChildCommentNum();
        commentRepository.save(comment);
    }

    /**
     * 함수명 : decreaseChildPostNum()
     * 함수 목적 : 답글 삭제 시 child_post_num 감소
     */
    private void decreaseChildCommentNum(Long parentCommentId) {
        Comment comment = commentRepository.findById(parentCommentId).orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
        comment.decreaseChildCommentNum();
        commentRepository.save(comment);
    }
}