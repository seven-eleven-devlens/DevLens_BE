package com.seveneleven.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchCommentRequest {

    @NotBlank
    private String content;

    @NotNull
    @Size(min = 1, max = 300)
    private Long modifierId;

    @NotBlank
    private String modifierIp;
}
