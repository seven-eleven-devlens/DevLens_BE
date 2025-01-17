package com.seveneleven.entity.board.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostFilter {

    TITLE("제목"),
    CONTENT("내용"),
    WRITER("작성자");

    private final String description;
}
