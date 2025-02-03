package com.seveneleven.board.controller;

import com.seveneleven.board.dto.PatchCommentRequest;
import com.seveneleven.board.dto.PostCommentRequest;
import com.seveneleven.board.service.CommentService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController implements CommentDocs {

    private final CommentService commentService;

    @PostMapping()
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> createComment(@PathVariable Long postId,
                                                                  @Valid @RequestBody PostCommentRequest postCommentRequest,
                                                                  HttpServletRequest request
    ) throws Exception {
        // todo : 토큰값으로 작성권한 확인 추가
        Long registerId = 1L;

        commentService.createComment(postId, postCommentRequest, request, registerId);
        return ResponseEntity.status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    @PatchMapping("/{commentId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> updateComment(@PathVariable Long postId,
                                                                  @PathVariable Long commentId,
                                                                  @RequestBody PatchCommentRequest patchCommentRequest,
                                                                  HttpServletRequest request
    ) throws Exception {
        // todo : 토큰값으로 작성권한 확인 추가
        Long modifierId = 1L;

        commentService.updateComment(postId, commentId, patchCommentRequest, request, modifierId);
        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED));
    }

    @DeleteMapping("{commentId}")
    @Override
    public ResponseEntity<APIResponse<SuccessCode>> deleteComment(@PathVariable Long postId,
                                                                  @PathVariable Long commentId,
                                                                  HttpServletRequest request
    ) throws Exception {
        // todo : 토큰값으로 작성권한 확인 추가
        Long deleterId = 1L;

        commentService.deleteComment(postId, commentId, request, deleterId);
        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }

}
