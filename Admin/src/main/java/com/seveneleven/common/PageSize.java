package com.seveneleven.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageSize {
    DEFAULT_PAGE_SIZE(10);
    private final int pageSize;
}
