package com.lukanikoloz.jobanalytics.Log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("Call method: " + joinPoint.getSignature().getName() + ", Args: "
                + Arrays.toString(joinPoint.getArgs()));

        Object o = joinPoint.proceed();

        if (o != null) {
            log.info(joinPoint.getSignature().getName() + " response: " + o.toString());
        } else {
            log.info(joinPoint.getSignature().getName() + " completed with no return value.");
        }

        return o;

    }

    @AfterThrowing(pointcut = "within(@org.springframework.web.bind.annotation.RestController *)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("my error --> Exception thrown in method: " + joinPoint.getSignature().getName() +
                ", Exception message: " + exception.getMessage());
    }

}
