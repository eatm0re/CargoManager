package com.tsystems.javaschool.evgenydubovitsky.cargomanager.util;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Logging {

    private static Logger logger = LoggerFactory.getLogger(Logging.class);

    @Around("@annotation(Loggable)")
    public Object debugLog(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        logger.debug("{} invoked in {} with arguments: {}", methodName, pjp.getTarget().getClass().getSimpleName(), Arrays.toString(pjp.getArgs()));
        try {
            Object res = pjp.proceed();
            logger.debug("{}({}) has successfully executed in {}. Returned value: {}", methodName, Arrays.toString(pjp.getArgs()), pjp.getTarget().getClass().getSimpleName(), res);
            return res;
        } catch (Throwable e) {
            String log = "{}({}) has thrown the {}: {}";
            if (e instanceof BusinessException) {
                logger.info(log, methodName, Arrays.toString(pjp.getArgs()), e.getClass().getSimpleName(), e.getMessage());
            } else {
                logger.error(log, methodName, Arrays.toString(pjp.getArgs()), e.getClass().getSimpleName(), e.getMessage());
            }
            throw e;
        }
    }

    @Around("@annotation(Request)")
    @Order(2)
    public Object requestLog(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName().toUpperCase();
        logger.info("Received request {} for {} with data contained: {}", methodName, pjp.getTarget().getClass().getSimpleName(), Arrays.toString(pjp.getArgs()));
        Object res = pjp.proceed();
        logger.info("{}.{}({}) responsed: {}", pjp.getTarget().getClass().getSimpleName(), methodName, Arrays.toString(pjp.getArgs()), res);
        return res;
    }
}
