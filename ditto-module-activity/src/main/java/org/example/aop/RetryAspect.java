package org.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE-1)
@Aspect
@Component
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable{

        Exception exceptionHolder = null;
        for (int attempt = 0; attempt < retry.maxRetries(); attempt++){
            try{
                return joinPoint.proceed();
            } catch (Exception e){
                exceptionHolder = e;
                Thread.sleep(retry.retryDelay());
            }
        }
        throw  exceptionHolder;
    }
}
