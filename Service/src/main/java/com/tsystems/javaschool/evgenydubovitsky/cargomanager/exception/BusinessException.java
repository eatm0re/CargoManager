package com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception;

public class BusinessException extends Exception {

    public BusinessException(String message) {
        super(message, null, false, false);
    }
}
