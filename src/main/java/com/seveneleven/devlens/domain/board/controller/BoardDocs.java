package com.seveneleven.devlens.domain.board.controller;

import com.seveneleven.devlens.domain.board.dto.PostCreateRequest;
import com.seveneleven.devlens.domain.board.dto.PostResponse;
import com.seveneleven.devlens.domain.board.dto.PostUpdateRequest;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "게시글 API", description = "게시글 관련 API")
public interface BoardDocs {
    // 조회
    @Operation(
            summary = "게시글 상세 조회",
            description = "게시글을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "조회할 게시글의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    @GetMapping("/posts/{postId}")
    ResponseEntity<APIResponse<PostResponse>> selectPost(@PathVariable Long postId) throws Exception;

    // 생성
    @Operation(
            summary = "게시글 생성",
            description = "새로운 게시글을 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "게시글 생성 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/posts")
    ResponseEntity<APIResponse<SuccessCode>> createPost(@RequestPart PostCreateRequest postCreateRequest,
                                                        @RequestPart(required = false) List<MultipartFile> files
    ) throws Exception;

    // 수정
    @Operation(
            summary = "게시물 수정",
            description = "기존 게시글을 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 수정 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "postId",
                            description = "수정할 게시글의 ID",
                            required = true,
                            example = "1"
                    )
            }
    )
    @PutMapping("/posts/{postId}")
    ResponseEntity<APIResponse<SuccessCode>> updatePost(@PathVariable Long postId,
                                                        @RequestPart PostUpdateRequest postUpdateRequest,
                                                        @RequestPart(required = false) List<MultipartFile> files
    ) throws Exception;

    // 삭제
    @Operation(
            summary = "게시글 삭제",
            description = "기존 게시글을 삭제합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 삭제 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            }
    )
    @DeleteMapping("/posts/{postId}/{registerId}")
    ResponseEntity<APIResponse<SuccessCode>> deletePost(@PathVariable Long postId,
                                                        @PathVariable Long registerId
    ) throws Exception;
}
