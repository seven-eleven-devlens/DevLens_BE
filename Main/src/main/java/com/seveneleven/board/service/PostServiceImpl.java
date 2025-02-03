package com.seveneleven.board.service;

import com.seveneleven.board.dto.*;
import com.seveneleven.entity.board.constant.PostAction;
import com.seveneleven.board.repository.PostHistoryRepository;
import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.PostHistory;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.entity.board.constant.PostSort;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import com.seveneleven.response.PaginatedResponse;
import com.seveneleven.util.GetIpUtil;
import com.seveneleven.util.file.dto.LinkInput;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seveneleven.board.dto.PostResponse.getPostResponse;
import static com.seveneleven.response.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostHistoryRepository postHistoryRepository;
    private final ProjectStepRepository projectStepRepository;
    private final MemberRepository memberRepository;
    private final PostFileService postFileService;
    private final CommentService commentService;
    private final PostLinkService postLinkService;

    private final GetIpUtil getIpUtil;

    private final int PAGE_SIZE = 10;

    /**
     * 함수명 : selectPostList()
     * 함수 목적 : 게시글 목록을 조회하는 메서드
     * param : 프로젝트 단계 ID, 현재 페이지 수, 입력 키워드, 필터, 정렬 타입
     */
    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<PostListResponse> selectPostList(Long projectStepId, Integer page, String keyword, PostFilter filter, PostSort sortType) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, getSort(sortType));

        String repoFilter = null;
        if(filter != null) {
            repoFilter = filter.name();
        }
        if(keyword != null) {
            keyword = keyword.trim();
        }

        Page<Post> repoPostList = postRepository.findAllByProjectStepId2(projectStepId, keyword, repoFilter, pageable);

        Page<PostListResponse> postListResponsePage = repoPostList.map(post -> {
            Member member = memberRepository.findById(post.getCreatedBy())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));
            return PostListResponse.toDto(post, member.getName());
        });

        return PaginatedResponse.createPaginatedResponse(postListResponsePage);
    }

    /**
     * 함수명 : selectPost()
     * 함수 목적 : 게시글 상세를 조회하는 메서드
     * param : 게시물 ID
     */
    @Transactional(readOnly = true)
    @Override
    public PostResponse selectPost(Long postId) {
        Post post = getPost(postId);

        String memberName = memberRepository.findNameById((post.getCreatedBy()))
                .orElseThrow(() -> new BusinessException(NOT_FOUND_WRITER));

        Long parentPostId = null;
        if (post.getParentPost() != null) {
            parentPostId = post.getParentPost().getId();
        }

        // 댓글 목록 조회
        List<GetCommentResponse> comments = commentService.selectCommentList(postId);

        return getPostResponse(post, parentPostId, memberName, comments);
    }

    /**
     * 함수명 : createPost()
     * 함수 목적 : 게시글을 생성하는 메서드
     */
    @Transactional
    @Override
    public void createPost(PostCreateRequest postCreateRequest, HttpServletRequest request, Long registerId) {
        String registerIp = getIpUtil.getIpAddress(request);

        // 원글인 경우
        if (postCreateRequest.getParentPostId() == null) {
            Post post = getCreatePost(
                    registerId,
                    postCreateRequest,
                    registerIp,
                    postRepository.findMaxRef() + 1,
                    0
            );
            savePostAndPostHistory(post, registerIp);

            // 요청 dto 에서 링크 리스트를 가져와서 게시물 링크 업로드
            uploadLink(postCreateRequest, post);
        } else {
            // 답글인 경우
            if(!matchesProjectStepParentAndChild(postCreateRequest.getProjectStepId(), postCreateRequest.getParentPostId())) {
                throw new BusinessException(NOT_MATCH_PROJECTSTEPID);
            }
            Post post = getCreatePost(
                    registerId,
                    postCreateRequest,
                    registerIp,
                    getRef(postCreateRequest.getParentPostId()),
                    getRefOrder(postCreateRequest.getParentPostId())+1
            );
            savePostAndPostHistory(post, registerIp);
            increaseChildPostNum(postCreateRequest.getParentPostId());

            // 요청 dto 에서 링크 리스트를 가져와서 게시물 링크 업로드
            uploadLink(postCreateRequest, post);
        }
    }

    /**
     * 함수명 : updatePost()
     * 함수 목적 : 게시글을 수정하는 메서드
     */
    @Transactional
    @Override
    public void updatePost(PostUpdateRequest postUpdateRequest, HttpServletRequest request, Long modifierId) {
        // 게시물 존재 여부 및 작성자 일치 여부 확인
        Post post = getPost(postUpdateRequest.getPostId());
        matchPostWriter(post.getCreatedBy(), modifierId);

        String modifierIp = getIpUtil.getIpAddress(request);
        // Post 테이블에서 기존 게시글 수정
        post.updatePost(
                postUpdateRequest.getPriority(),
                postUpdateRequest.getStatus(),
                postUpdateRequest.getTitle(),
                postUpdateRequest.getContent(),
                postUpdateRequest.getDeadline(),
                modifierIp
        );

        postRepository.save(post);
        postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.UPDATE, modifierIp));
    }

    /**
     * 함수명 : deletePost()
     * 함수 목적 : 게시글을 삭제하는 메서드
     * param : 게시글 ID, 등록자 ID
     */
    @Transactional
    public void deletePost(Long postId, HttpServletRequest request, Long deleterId) {

        // 게시물 존재 여부 및 작성자 일치 여부 확인
        Post post = getPost(postId);
        matchPostWriter(post.getCreatedBy(), deleterId);

        String modifierIp = getIpUtil.getIpAddress(request);

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

        //해당 게시물의 링크 일괄 삭제
        postLinkService.deleteAllPostLinks(postId);

        //게시물 파일 일괄 삭제
        postFileService.deleteAllPostFiles(post.getId(), deleterId);

        postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.DELETE, modifierIp));
        postRepository.delete(post);
    }

    /**
     * 함수명 : getSort()
     * 함수 목적 : 게시물 목록 조회 시 sortType 에 따른 정렬 기준 반환 메서드
     */
    private Sort getSort(PostSort sortType) {
        Sort sort = Sort.by(Sort.Order.desc("ref"), Sort.Order.asc("refOrder")); // 기본 정렬 기준 (최신순)
        if (PostSort.OLDEST.equals(sortType)) {
            sort = Sort.by(Sort.Order.asc("ref"), Sort.Order.asc("refOrder"));  // (오래된순)
        }
        return sort;
    }

    /**
     * 함수명 : savePostAndPostHistory()
     * 함수 목적 : 게시글 및 게시글 이력 저장 메서드
     */
    private void savePostAndPostHistory(Post post, String registerIp) {
        postRepository.save(post);
        postHistoryRepository.save(PostHistory.createPostHistory(post, PostAction.CREATE, registerIp));
    }

    /**
     * 함수명 : uploadLink()
     * 함수 목적 : 요청 dto 에서 링크 리스트를 가져와 게시물 링크를 업로드하는 메서드
     */
    private void uploadLink(PostCreateRequest postCreateRequest, Post post) {
        //요청 dto 에서 링크 리스트 가져오기
        List<LinkInput> linkInputs = postCreateRequest.getLinkInputList();
        //게시물 링크 업로드
        postLinkService.uploadPostLinks(linkInputs, post.getId(), post.getCreatedBy());
    }

    /**
     * 함수명 : getParentPost()
     * 함수 목적 : 부모게시물 여부 확인 메서드
     */
    private Post getParentPost(Post post) {
        if(post.getParentPost() != null) {
            return postRepository.findById(post.getParentPost().getId())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        }
        return null;
    }

    /**
     * 함수명 : getPost()
     * 함수 목적 : 게시물 존재 여부 확인 메서드
     */
    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    /**
     * 함수명 : matchPostWriter()
     * 함수 목적 : 게시물 작성자 일치 여부 확인 메서드
     */
    private void matchPostWriter(Long createdBy, Long modifierId) {
        if(!createdBy.equals(modifierId)) {
            throw new BusinessException(NOT_MATCH_WRITER);
        }
    }

    /**
     * 함수명 : getCreatePost()
     * 함수 목적 : (원글,답글) 게시글 생성 메서드
     */
    private Post getCreatePost(Long registerId, PostCreateRequest postCreateRequest, String registerIp, Long ref, Integer refOrder) {
        // 프로젝트 단계 확인
        ProjectStep projectStep = projectStepRepository.findById(postCreateRequest.getProjectStepId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_PROJECT_STEP));
        // 부모게시글 확인
        Post parentPost = null;
        if(postCreateRequest.getParentPostId() != null) {
            parentPost = postRepository.findById(postCreateRequest.getParentPostId())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_PROJECT_STEP));
        }
        // 작성자 확인
        Member member = memberRepository.findById(registerId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));

        return Post.createPost(
                projectStep,
                parentPost,
                ref,
                refOrder,
                0,                      // CHILD_POST_NUM = 0
                postCreateRequest.getPriority(),
                postCreateRequest.getStatus(),
                postCreateRequest.getTitle(),
                postCreateRequest.getContent(),
                member.getName(),
                postCreateRequest.getDeadline(),
                registerIp,
                null
        );
    }

    /**
     * 함수명 : matchesProjectStepParentAndChild()
     * 함수 목적 : (답글) 부모게시글과 자식게시글의 프로젝트 단계 일치 여부 확인 메서드
     */
    private boolean matchesProjectStepParentAndChild(Long childProjectStepId, Long childParentPostId) {
        // 부모게시글의 프로젝트 단계 값 받기
        Post post = postRepository.findById(childParentPostId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        return post.getProjectStep().getId().equals(childProjectStepId);
    }

    /**
     * 함수명 : getRef()
     * 함수 목적 : (답글) 원글의 REF 반환 메서드
     */
    private Long getRef(Long parentPostId) {
        Post post = postRepository.findById(parentPostId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        if (post.getRef() == null) {
            throw new BusinessException(NOT_FOUND_POST);
        }
        return  post.getRef();
    }

    /**
     * 함수명 : getRefOrder()
     * 함수 목적 : (답글) 동일 그룹의 REF_ORDER 최대값 반환 메서드
     */
    private Integer getRefOrder(Long parentPostId) {
        if(postRepository.findById(parentPostId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST)).getChildPostNum().equals(0)) {
            return 0;
        }
        return postRepository.findMaxRefOrderByParentPostId(parentPostId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    /**
     * 함수명 : increaseChildPostNum()
     * 함수 목적 : 답글 생성 시 child_post_num 증가
     */
    private void increaseChildPostNum(Long parentPostId) {
        Post post = postRepository.findById(parentPostId).orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        post.increaseChildPostNum();
    }

    /**
     * 함수명 : decreaseChildPostNum()
     * 함수 목적 : 답글 삭제 시 child_post_num 감소
     */
    private void decreaseChildPostNum(Long parentPostId) {
        Post post = postRepository.findById(parentPostId).orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
        post.decreaseChildPostNum();
    }
}