package com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception;

public class EntityExistsException extends BusinessException {

    private static final int HTTP_CODE = 409;

    public EntityExistsException(String message) {
        super(message);
    }

    @Override
    public int getHttpCode() {
        return HTTP_CODE;
    }
}
