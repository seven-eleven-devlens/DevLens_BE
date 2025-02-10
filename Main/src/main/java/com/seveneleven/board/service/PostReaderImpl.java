package com.seveneleven.board.service;

import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static com.seveneleven.response.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {
    private final PostRepository postRepository;
    private final ProjectStepRepository projectStepRepository;
    private final MemberRepository memberRepository;

    @Override
    public void existPost(Long postId) {
        if(!postRepository.existsById(postId)) {
            throw new BusinessException(NOT_FOUND_POST);
        }
    }

    @Override
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    @Override
    public Page<Post> getPosts(Long projectStepId, String keyword, String repoFilter, Pageable pageable) {
        return postRepository.findAllByProjectStepId(projectStepId, keyword, repoFilter, pageable);
    }

    @Override
    public Long getRef(Long postId) {
        return postRepository.findRefById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    @Override
    public Long getMaxRef() {
        return postRepository.findMaxRef();
    }

    @Override
    public Integer getRefOrder(Post parentPost) {
        if(parentPost.getChildPostNum().equals(0)) {
            return 0;
        }
        return postRepository.findMaxRefOrderByParentPostId(parentPost.getId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }


    @Override
    public ProjectStep getProjectStep(Long projectStepId) {
        return projectStepRepository.findById(projectStepId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_PROJECT_STEP));
    }

    @Override
    public String getWriter(Long memberId) {
        return memberRepository.findNameById(memberId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_WRITER));
    }


}
