package com.tsystems.javaschool.evgenydubovitsky.cargomanager.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Logging {

    private static Logger logger = LoggerFactory.getLogger(Logging.class);

    @Around("@annotation(Loggable)")
    public Object methodAroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        logger.debug(methodName + " invoked in " + pjp.getTarget().getClass().getSimpleName() + " with arguments: " + Arrays.toString(pjp.getArgs()));
        try {
            Object res = pjp.proceed();
            logger.debug(methodName + "(" + Arrays.toString(pjp.getArgs()) + ") successfully executed in " + pjp.getTarget().getClass().getSimpleName() + ". Returned value: " + res);
            return res;
        } catch (Throwable e) {
            logger.warn(methodName + "(" + Arrays.toString(pjp.getArgs()) + ") has thrown the " + e.getClass().getSimpleName() + ": " + e.getMessage());
            throw e;
        }
    }
}
