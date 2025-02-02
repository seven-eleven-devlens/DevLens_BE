package com.seveneleven.config.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.util.GetIpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class ServiceAop {

    private final GetIpUtil getIpUtil = new GetIpUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("execution(* com.seveneleven.*.service..*ServiceImpl.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Service - {} called | args : {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            log.info("Service - {} end | return : {}", joinPoint.getSignature().getName(), result);
            return result;
        } catch (BusinessException bs) {
            log.error("Service - {} end | error code : {}, message : {}", joinPoint.getSignature().getName(), bs.getErrorCode().getStatusCode(), bs.getMessage());
            throw bs;
        }
    }
}
