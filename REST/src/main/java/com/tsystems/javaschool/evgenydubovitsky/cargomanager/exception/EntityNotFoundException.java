package com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception;

public class EntityNotFoundException extends BusinessException {

    private static final int HTTP_CODE = 404;

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getHttpCode() {
        return HTTP_CODE;
    }
}
