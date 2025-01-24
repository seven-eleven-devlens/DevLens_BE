package com.seveneleven.config;

import com.seveneleven.util.GetIpUtil;
import com.seveneleven.util.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEventListener.class);
    private final GetIpUtil getIpUtil = new GetIpUtil();

    @Async
    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        CustomUserDetails userDetails = (CustomUserDetails) event.getAuthentication().getPrincipal();

        logger.info("Authentication Success: UserId = {}", userDetails.getId());
    }

    @Async
    @EventListener
    public void onAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
        logger.warn("Authentication Failed: Username = {}, Reason = {}",
                event.getAuthentication().getName(),
                event.getException().getMessage());
    }
}
