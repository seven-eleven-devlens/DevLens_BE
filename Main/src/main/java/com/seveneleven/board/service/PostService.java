package com.seveneleven.board.service;

import com.seveneleven.entity.board.constant.PostAction;
import com.seveneleven.board.dto.PostCreateRequest;
import com.seveneleven.board.dto.PostResponse;
import com.seveneleven.board.dto.PostUpdateRequest;
import com.seveneleven.board.repository.PostHistoryRepository;
import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.PostHistory;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.seveneleven.response.ErrorCode.*;

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
    @Transactional(readOnly = true)
    public PostResponse selectPost(Long postId) throws Exception {
        Post post = getPost(postId);

        String memberName = memberRepository.findNameById((post.getCreatedBy()))
                .orElseThrow(() -> new BusinessException(NOT_FOUND_WRITER));

        Long parentPostId = null;
        if (post.getParentPostId() != null) {
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
        원글 생성 : REF = REF의 최대값 + 1  / REF_ORDER = 0                      / CHILD_POST_NUM = 0
        답글 생성 : REF = 원글의 REF와 동일  / REF_ORDER = 그룹의 ORDER 최대값 + 1   / CHILD_POST_NUM = 답글은 0, 원글은 기존값에 + 1
     */
    @Transactional
    public void createPost(PostCreateRequest postCreateRequest) throws Exception {
        // todo : 작성권한 확인 로직 추가 예정

        // 원글인 경우
        if (postCreateRequest.getParentPostId() == null) {
            Post post = getCreatePost(postCreateRequest, postRepository.findFirstRefByOrderByRefDesc() + 1, 0);
            postRepository.save(post);
            postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.CREATE));

            // todo: 파일, 링크 저장 로직 추가 예정
        } else {
        // 답글인 경우
            if(!matchesProjectStepParentAndChild(postCreateRequest)) {
                throw new BusinessException(NOT_MATCH_PROJECTSTEPID);
            }
            Post post = getCreatePost(postCreateRequest, getRef(postCreateRequest.getParentPostId()), getRefOrder(postCreateRequest.getParentPostId())+1);
            postRepository.save(post);
            postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.CREATE));

            // 원글의 Child_Post_Num 컬럼값에 +1
            increaseChildPostNum(postCreateRequest.getParentPostId());

            // todo: 파일, 링크 저장 로직 추가 예정
        }
    }

    /*
        함수명 : updatePost
        함수 목적 : 게시글 수정
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
        원글 삭제 : 답글이 없는 경우에만 삭제 가능
        답글 삭제 : 원글의 child_post_num 값을 -1 하고, 테이블에서 데이터 삭제
     */
    @Transactional
    public void deletePost(Long postId, Long registeredId) throws Exception {
        Post post = checkPostAndWriter(postId, registeredId);

        // 원글인 경우
        if(getParentPost(post) == null) {
            if(post.getChildPostNum() >= 1) {
                // 답글이 한 개 이상 존재하는 경우, 삭제 실패
                throw new BusinessException(NOT_DELETE_PARENT_POST);
            }
        } else {
            // 답글인 경우
            Post parentPost = getParentPost(post);
            decreaseChildPostNum(parentPost.getId());
        }
        postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.DELETE));
        postRepository.delete(post);
    }


    // 부모게시물 여부 확인 메서드 (Post ParentPost)
    private Post getParentPost(Post post) throws Exception {
        if(post.getParentPostId() != null) {
            return postRepository.findById(post.getParentPostId().getId())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        }
        return null;
    }

    // 게시물 존재 여부 확인 메서드
    private Post getPost(Long postId) throws Exception {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    // 게시물 존재 및 작성자 일치 여부 확인 메서드
    private Post checkPostAndWriter(Long postId, Long registeredId) throws Exception {
        Post post = getPost(postId);
        if (!post.getCreatedBy().equals(registeredId)) {
            throw new BusinessException(NOT_AUTHORIZED_TO_DELETE);
        }
        return post;
    }

    // (원글,답글) 게시글 생성 메서드
    private Post getCreatePost(PostCreateRequest postCreateRequest, Long ref, Integer refOrder) throws Exception {
        // 프로젝트 단계 확인
        ProjectStep projectStep = projectStepRepository.findById(postCreateRequest.getProjectStepId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_PROJECT_STEP));
        // 부모게시글 확인
        Post parentPost = null;
        if(postCreateRequest.getParentPostId() != null) {
            parentPost = postRepository.findById(postCreateRequest.getParentPostId())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_PROJECT_STEP));
        }

        return Post.createPost(
                projectStep,
                parentPost,
                ref,
                refOrder,
                0,                      // CHILD_POST_NUM = 0
                postCreateRequest.getIsPinnedPost(),
                postCreateRequest.getPriority(),
                postCreateRequest.getStatus(),
                postCreateRequest.getTitle(),
                postCreateRequest.getContent(),
                postCreateRequest.getDeadline(),
                postCreateRequest.getRegisterIp(),
                postCreateRequest.getRegisterIp()
        );
    }

    // (답글) 부모게시글과 자식게시글의 프로젝트 단계 일치 여부 확인 메서드
    private boolean matchesProjectStepParentAndChild(PostCreateRequest postCreateRequest) throws Exception {
        // 답글의 projectStepId 와 parentPostId
        Long childProjectStepId = postCreateRequest.getProjectStepId();
        Long parentPostId = postCreateRequest.getParentPostId();

        // 원글의 parentPost 와 parentProjectStepId
        Post parentPost = postRepository.findById(parentPostId).get();
        Long parentProjectStepId = parentPost.getProjectStepId().getId();

        return Objects.equals(childProjectStepId, parentProjectStepId);
    }

    // (답글) 원글의 REF 반환 메서드
    private Long getRef(Long parentPostId) throws Exception {
        Post post = postRepository.findById(parentPostId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        if (post.getRef() == null) {
            throw new BusinessException(NOT_FOUND_POST);
        }
        return  post.getRef();
    }

    // (답글) 동일 그룹의 REF_ORDER 최대값 반환 메서드
    private Integer getRefOrder(Long parentPostId) throws Exception {
        return postRepository.findMaxRefOrderByParentPostId(parentPostId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    // (답글) 답글 생성 시 child_post_num 증가
    private void increaseChildPostNum(Long parentPostId) throws Exception {
        Post post = postRepository.findById(parentPostId).orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        post.increaseChildPostNum();
        postRepository.save(post);
    }

    // (답글) 답글 삭제 시 child_post_num 감소
    private void decreaseChildPostNum(Long parentPostId) throws Exception {
        Post post = postRepository.findById(parentPostId).orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        post.decreaseChildPostNum();
        postRepository.save(post);
    }
}
