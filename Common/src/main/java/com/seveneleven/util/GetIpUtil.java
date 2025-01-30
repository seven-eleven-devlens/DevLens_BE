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
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr(); // 클라이언트의 실제 IP 가져오기
        } else {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
