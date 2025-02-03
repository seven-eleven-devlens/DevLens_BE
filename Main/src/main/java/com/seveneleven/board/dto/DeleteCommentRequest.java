package com.seveneleven.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteCommentRequest {

    @NotNull
    private Long modifierId;

    @NotBlank
    private String modifierIp;
}
