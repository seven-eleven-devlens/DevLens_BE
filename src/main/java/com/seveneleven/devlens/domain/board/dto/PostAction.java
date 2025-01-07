package com.seveneleven.devlens.domain.board.dto;

import lombok.Getter;

@Getter
public enum PostAction {

    CREATE("생성"),
    UPDATE("수정"),
    DELETE("삭제");

    private final String description;

    PostAction(String description) {
        this.description = description;
    }
}
