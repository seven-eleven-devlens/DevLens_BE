package com.seveneleven.devlens.domain.board.service;

import com.seveneleven.devlens.domain.board.dto.PostAction;
import com.seveneleven.devlens.domain.board.dto.PostCreateRequest;
import com.seveneleven.devlens.domain.board.dto.PostResponse;
import com.seveneleven.devlens.domain.board.dto.PostUpdateRequest;
import com.seveneleven.devlens.domain.board.entity.Post;
import com.seveneleven.devlens.domain.board.entity.PostHistory;
import com.seveneleven.devlens.domain.board.repository.PostHistoryRepository;
import com.seveneleven.devlens.domain.board.repository.PostRepository;
import com.seveneleven.devlens.domain.member.repository.MemberRepository;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import com.seveneleven.devlens.domain.project.repository.ProjectStepRepository;
import com.seveneleven.devlens.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.seveneleven.devlens.global.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostHistoryRepository postHistoryRepository;
    private final ProjectStepRepository projectStepRepository;
    private final MemberRepository memberRepository;

    /*
        함수명 : selectPost
        함수 목적 : 게시글 조회
        param : 게시물 ID
     */
    public PostResponse selectPost(Long postId) throws Exception {
        Post post = getPost(postId);

        String memberName = memberRepository.findNameById((post.getCreatedBy()))
                .orElseThrow(() -> new BusinessException(NOT_FOUND_WRITER));

        Long parentPostId = null;
        if(post.getParentPostId() != null) {
            parentPostId = post.getParentPostId().getId();
        }

        return new PostResponse(
                post.getId(),
                post.getProjectStepId().getId(),
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

    /*
        함수명 : createPost
        함수 목적 : 게시글 생성
        postCreateRequest : 프로젝트 단계 ID, 부모 게시물 ID, 제목, 내용, 상태, 상단고정여부, 작성자, 작성일시, 마감일자
     */
    @Transactional
    public void createPost(PostCreateRequest postCreateRequest) throws Exception {
        // todo : 작성권한 확인 로직 추가 예정

        // 게시글 등록자 존재 여부 확인
        if (memberRepository.existsById(postCreateRequest.getRegisterId())) {
            Post post = Post.createPost(
                    getProjectStepId(postCreateRequest),    // 프로젝트 단계 여부 확인
                    getParentPostId(postCreateRequest),     // 부모게시물 존재 여부 확인
                    postCreateRequest.getIsPinnedPost(),
                    postCreateRequest.getPriority(),
                    postCreateRequest.getStatus(),
                    postCreateRequest.getTitle(),
                    postCreateRequest.getContent(),
                    postCreateRequest.getDeadline(),
                    postCreateRequest.getRegisterIp(),
                    postCreateRequest.getRegisterIp()
            );

            postRepository.save(post);
            postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.CREATE));

            // todo: 파일, 링크 저장 로직 추가 예정
            // param : 파일 리스트, postId, memberID
            // for(file 하나씩)
            return;
        }
        throw new BusinessException(NOT_FOUND_MEMBER);
    }

    /*
        함수명 : updatePost
        함수 목적 : 게시글 수정
        postUpdateRequest : 게시물 ID, 프로젝트 단계 ID, 부모 게시물 ID, 제목, 내용, 상태, 상단고정여부, 등록자 ID, 수정자 ID, 수정자 IP, 수정일시, 마감일자
     */
    @Transactional
    public void updatePost(PostUpdateRequest postUpdateRequest) throws Exception {
        // 게시물 존재 여부 및 작성자 일치 여부 확인
        Post post = checkPostAndWriter(postUpdateRequest.getPostId(), postUpdateRequest.getModifierId());

        // Post 테이블에서 기존 게시글 수정
        post.updatePost(
                postUpdateRequest.getIsPinnedPost(),
                postUpdateRequest.getPriority(),
                postUpdateRequest.getStatus(),
                postUpdateRequest.getTitle(),
                postUpdateRequest.getContent(),
                postUpdateRequest.getDeadline(),
                postUpdateRequest.getModifierIp()
                );

        postRepository.save(post);
        postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.UPDATE));

        // todo: 파일, 링크 저장 로직 추가 예정
    }

    /*
        함수명 : deletePost
        함수 목적 : 게시글 삭제 메서드
        param : postId, registeredId
     */
    @Transactional
    public void deletePost(Long postId, Long registeredId) throws Exception {
        Post post = checkPostAndWriter(postId, registeredId);

        postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.DELETE));
        postRepository.delete(post);
    }


    // 프로젝트 단계 여부 확인 메서드
    private ProjectStep getProjectStepId(PostCreateRequest postCreateRequest) throws Exception {
        return projectStepRepository.findById(postCreateRequest.getProjectStepId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_PROJECT_STEP));
    }

    // 부모게시물 존재 여부 확인 메서드
    private Post getParentPostId(PostCreateRequest postCreateRequest) throws Exception {
        if(postCreateRequest.getParentPostId() != null) {
            return postRepository.findById(postCreateRequest.getParentPostId())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        }
        return null;
    }

    // 게시물 존재 여부 확인 메서드
    private Post getPost(Long postId) throws Exception {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        return post;
    }

    // 게시물 존재 및 작성자 일치 여부 확인 메서드
    private Post checkPostAndWriter(Long postId, Long registeredId) throws Exception {
        Post post = getPost(postId);
        if (!post.getCreatedBy().equals(registeredId)) {
            throw new BusinessException(NOT_AUTHORIZED_TO_DELETE);
        }
        return post;
    }
}
