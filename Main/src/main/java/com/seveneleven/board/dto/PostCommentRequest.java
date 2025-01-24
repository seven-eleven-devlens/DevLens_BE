package com.seveneleven.board.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentRequest {

    @Nullable
    private Long parentCommentId;

    @NotBlank
    private String content;

    @NotBlank
    private String registerIp;
}
