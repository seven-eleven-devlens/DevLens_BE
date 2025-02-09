package com.seveneleven.board.service;

import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.project.ProjectStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReader {
    void existPost(Long postId);

    Post getPost(Long postId);

    Page<Post> getPosts(Long projectStepId, String keyword, String repoFilter, Pageable pageable);

    ProjectStep getProjectStep(Long projectStepId);

    String getWriter(Long memberId);

    Long getRef(Long postId);

    Long getMaxRef();

    Integer getRefOrder(Post parentPost);
}
