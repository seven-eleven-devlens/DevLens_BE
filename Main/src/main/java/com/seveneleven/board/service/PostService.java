package com.seveneleven.board.service;

import com.seveneleven.board.dto.PostCreateRequest;
import com.seveneleven.board.dto.PostListResponse;
import com.seveneleven.board.dto.PostResponse;
import com.seveneleven.board.dto.PostUpdateRequest;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.entity.board.constant.PostSort;
import com.seveneleven.response.PaginatedResponse;
import com.seveneleven.util.security.dto.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PostService {

    PaginatedResponse<PostListResponse> selectPosts(Boolean isAllStages, Long projectId, Long projectStepId, Integer page, String keyword, PostFilter filter, PostSort sortType);
    PostResponse selectPost(Long postId, Long userId);
    Map<String, Long> createPost(PostCreateRequest postCreateRequest, HttpServletRequest request, String registerName);
    void updatePost(PostUpdateRequest postUpdateRequest, HttpServletRequest request, CustomUserDetails user);
    void deletePost(Long postId, HttpServletRequest request, CustomUserDetails user);
}
