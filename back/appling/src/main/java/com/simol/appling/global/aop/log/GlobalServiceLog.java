package com.simol.appling.global.aop.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class GlobalServiceLog {
    @Around("execution(* com.simol.appling..*.service..*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("[appling] method = [{}]", pjp.getSignature().toShortString());

        return pjp.proceed();
    }
}
