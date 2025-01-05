package com.seveneleven.devlens.global.util.file.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LinkHistoryType {
    LINK_HISTORY_REGISTER("링크 등록 이력"),
    LINK_HISTORY_DELETE("링크 삭제 이력");

    private final String description;
}
