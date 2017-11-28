package com.tsystems.javaschool.evgenydubovitsky.cargomanager.util;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
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
    public Object debugLog(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        logger.debug(methodName + " invoked in " + pjp.getTarget().getClass().getSimpleName() + " with arguments: " + Arrays.toString(pjp.getArgs()));
        try {
            Object res = pjp.proceed();
            logger.debug(methodName + "(" + Arrays.toString(pjp.getArgs()) + ") has successfully executed in " + pjp.getTarget().getClass().getSimpleName() + ". Returned value: " + res);
            return res;
        } catch (Throwable e) {
            String log = methodName + "(" + Arrays.toString(pjp.getArgs()) + ") has thrown the " + e.getClass().getSimpleName() + ": " + e.getMessage();
            if (e instanceof BusinessException) {
                logger.info(log);
            } else {
                logger.error(log);
            }
            throw e;
        }
    }

    @Around("@annotation(LoggableRequest)")
    public Object requestLog(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        logger.info("Received request " + methodName.toUpperCase() + " for " + pjp.getTarget().getClass().getSimpleName() + " with data contained: " + Arrays.toString(pjp.getArgs()));
        Object res = pjp.proceed();
        logger.info(methodName.toUpperCase() + "(" + Arrays.toString(pjp.getArgs()) + ") response body for " + pjp.getTarget().getClass().getSimpleName() + ": " + res);
        return res;
    }
}
