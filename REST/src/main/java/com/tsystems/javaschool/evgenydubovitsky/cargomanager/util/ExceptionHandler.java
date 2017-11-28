package com.tsystems.javaschool.evgenydubovitsky.cargomanager.util;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller.Response;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;

@Aspect
@Component
public class ExceptionHandler {

    @Around("@annotation(Request)")
    @Order(1)
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (BusinessException e) {
            return new Response(e.getHttpCode(), e.getMessage(), e.getClass().getSimpleName());
        } catch (PersistenceException e) {
            return new Response(503, "Can not access the database. Please try again later or contact your administrator", e.getMessage());
        } catch (Exception e) {
            return new Response(520, "Unknown server error. Please contact your administrator", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
