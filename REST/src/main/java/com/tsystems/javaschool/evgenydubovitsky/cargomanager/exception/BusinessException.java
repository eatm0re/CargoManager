package com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception;

public class BusinessException extends Exception {

    private static final int HTTP_CODE = 406;

    public BusinessException(String message) {
        super(message, null, false, false);
    }

    public int getHttpCode() {
        return HTTP_CODE;
    }
}
