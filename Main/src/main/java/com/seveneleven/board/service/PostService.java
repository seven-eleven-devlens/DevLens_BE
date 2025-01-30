package com.seveneleven.board.service;

import com.seveneleven.board.dto.PostCreateRequest;
import com.seveneleven.board.dto.PostListResponse;
import com.seveneleven.board.dto.PostResponse;
import com.seveneleven.board.dto.PostUpdateRequest;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.entity.board.constant.PostSort;
import com.seveneleven.response.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PostService {

    PaginatedResponse<PostListResponse> selectPostList(Long projectStepId, Integer page, String keyword, PostFilter filter, PostSort sortType);
    PostResponse selectPost(Long postId) throws Exception;
    void createPost(PostCreateRequest postCreateRequest, HttpServletRequest request) throws Exception;
    void updatePost(PostUpdateRequest postUpdateRequest, HttpServletRequest request) throws Exception;
    void deletePost(Long postId, Long registerId, HttpServletRequest request) throws Exception;
}
