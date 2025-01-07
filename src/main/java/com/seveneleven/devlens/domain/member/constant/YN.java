package com.seveneleven.devlens.domain.member.constant;

import java.util.List;

public enum YN {
    Y, N;

    public static YN isY(List<?> values) {
        if (values == null || values.isEmpty()) {
            return N;
        }
        return Y;
    }
}
