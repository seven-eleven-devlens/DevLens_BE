package com.seveneleven.devlens.domain.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostAction {

    CREATE("생성"),
    UPDATE("수정"),
    DELETE("삭제");

    private final String description;
}
