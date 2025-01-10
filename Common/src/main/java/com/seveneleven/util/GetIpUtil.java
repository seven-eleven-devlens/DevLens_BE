package com.seveneleven.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetIpUtil {

    public String getIpAddress(
            HttpServletRequest request
    ) {
        return request.getHeader("X-Forwarded-For").split(",")[0].trim();
    }
}
