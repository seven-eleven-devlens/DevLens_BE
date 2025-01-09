package com.seveneleven.devlens.domain.board.controller;

import com.seveneleven.devlens.domain.board.dto.PostCreateRequest;
import com.seveneleven.devlens.domain.board.dto.PostResponse;
import com.seveneleven.devlens.domain.board.dto.PostUpdateRequest;
import com.seveneleven.devlens.domain.board.service.PostService;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController implements BoardDocs {

    @Autowired
    PostService postService;

    // 조회
    @GetMapping("/posts/{postId}")
    @Override
    public ResponseEntity<APIResponse<PostResponse>> selectPost(@PathVariable Long postId) throws Exception {
        PostResponse postResponse = postService.selectPost(postId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                        .body(APIResponse.success(SuccessCode.OK, postResponse));
    }

    // 생성
    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> createPost(@RequestPart PostCreateRequest postCreateRequest,
                                                               @RequestPart(required = false) List<MultipartFile> files
    ) throws Exception {
        postService.createPost(postCreateRequest);

        // todo: 파일&링크 관련 메서드 추가 예정

        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    // 수정
    @PutMapping(value = "/posts/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> updatePost(@PathVariable Long postId,
                                                               @RequestPart PostUpdateRequest postUpdateRequest,
                                                               @RequestPart(required = false) List<MultipartFile> files
    ) throws Exception {
        postService.updatePost(postUpdateRequest);

        // todo: 파일&링크 관련 메서드 추가 예정

        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED));
    }

    // 삭제
    @DeleteMapping("/posts/{postId}/{registerId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> deletePost(@PathVariable Long postId,
                                                               @PathVariable Long registerId
    ) throws Exception {
        postService.deletePost(postId, registerId);

        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }


}
