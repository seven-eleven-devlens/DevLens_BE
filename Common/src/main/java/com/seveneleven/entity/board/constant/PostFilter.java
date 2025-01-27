package com.seveneleven.entity.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostFilter {
    ALL("모두"),
    TITLE("제목"),
    CONTENT("내용"),
    WRITER("작성자");

    private final String description;
}
