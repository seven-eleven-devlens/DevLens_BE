package com.seveneleven.board.controller;

import com.seveneleven.board.dto.*;
import com.seveneleven.board.service.CommentService;
import com.seveneleven.board.service.PostFileService;
import com.seveneleven.board.service.PostLinkService;
import com.seveneleven.board.service.PostServiceImpl;
import com.seveneleven.board.service.PostService;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.PageResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.file.dto.FileMetadataDto;
import com.seveneleven.util.file.dto.LinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class BoardController implements BoardDocs {

    private final PostService postService;
    private final CommentService commentService;
    private final PostFileService postFileService;
    private final PostLinkService postLinkService;

    /**
     * 함수명 : selectList()
     * 게시글 목록을 조회하는 메서드
     */
    @GetMapping("/steps/{projectStepId}")
    public ResponseEntity<APIResponse<PageResponse<PostListResponse>>> selectList (@PathVariable Long projectStepId,
                                                                                   @RequestParam(defaultValue = "0") Integer page,
                                                                                   @RequestParam(required = false) String keyword,
                                                                                   @RequestParam(required = false) PostFilter filter
                                                                                   // todo: 정렬기준 추후 추가 예정
    ) {
        PageResponse<PostListResponse> postList = postService.selectPostList(projectStepId, page, keyword, filter);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, postList));
    }

    /**
     * 함수명 : selectPost()
     * 게시글 상세를 조회하는 메서드
     */
    @GetMapping("/{postId}")
    @Override
    public ResponseEntity<APIResponse<PostResponse>> selectPost(@PathVariable Long postId) throws Exception {
        PostResponse postResponse = postService.selectPost(postId);
        postResponse.setComments(commentService.selectCommentList(postId));

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                        .body(APIResponse.success(SuccessCode.OK, postResponse));
    }

    /**
     * 함수명 : selectPostLinks
     * 게시글의 링크 목록을 불러오는 메서드
     */
    @GetMapping("/{postId}/links")
    public ResponseEntity<APIResponse<List<LinkResponse>>> selectPostLinks(@PathVariable Long postId) throws Exception {
        List<LinkResponse> postLists = postLinkService.getPostLinks(postId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, postLists));
    }

    /**
     * 함수명 : selectPostFiles
     * 게시글의 파일 목록을 불러오는 메서드
     */
    @GetMapping("/{postId}/files")
    public ResponseEntity<APIResponse<List<FileMetadataDto>>> selectPostFiles(@PathVariable Long postId) {
        List<FileMetadataDto> fileDataDtos = postFileService.getPostFiles(postId);

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, fileDataDtos));
    }

    /**
     * 함수명 : createPost()
     * 게시글을 생성하는 메서드
     */
    @PostMapping()
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> createPost(@RequestBody() PostCreateRequest postCreateRequest
    ) throws Exception {
        postService.createPost(postCreateRequest);

        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    /**
     * 함수명 : updatePost()
     * 게시글을 수정하는 메서드
     */
    @PutMapping(value = "/{postId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> updatePost(@PathVariable Long postId,
                                                               @RequestBody PostUpdateRequest postUpdateRequest
    ) throws Exception {
        postService.updatePost(postUpdateRequest);

        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED));
    }

    /**
     * 함수명 : deletePost()
     * 게시글을 삭제하는 메서드
     */
    @DeleteMapping("/{postId}/{registerId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> deletePost(@PathVariable Long postId,
                                                               @PathVariable Long registerId
    ) throws Exception {
        postService.deletePost(postId, registerId);
        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }


}
