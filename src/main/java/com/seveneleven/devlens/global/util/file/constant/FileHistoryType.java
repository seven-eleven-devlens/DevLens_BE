package com.seveneleven.devlens.global.util.file.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileHistoryType {
    FILE_METADATA_HISTORY_REGISTER("파일 등록 이력"),
    FILE_METADATA_HISTORY_DELETE("파일 삭제 이력");

    private final String description;
}
