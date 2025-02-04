package com.seveneleven.board.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentRequest {

    @Nullable
    private Long parentCommentId;

    @NotBlank
    @Size(min = 1, max = 300)
    private String content;
}
