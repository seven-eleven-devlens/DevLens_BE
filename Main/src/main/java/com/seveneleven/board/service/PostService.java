package com.seveneleven.board.service;

import com.seveneleven.board.dto.PostCreateRequest;
import com.seveneleven.board.dto.PostListResponse;
import com.seveneleven.board.dto.PostResponse;
import com.seveneleven.board.dto.PostUpdateRequest;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.response.PaginatedResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PaginatedResponse<PostListResponse> selectPostList(Long projectStepId, Integer page, String keyword, PostFilter filter);
    PostResponse selectPost(Long postId) throws Exception;
    void createPost(PostCreateRequest postCreateRequest) throws Exception;
    void updatePost(PostUpdateRequest postUpdateRequest) throws Exception;
    void deletePost(Long postId, Long registerId) throws Exception;
}
