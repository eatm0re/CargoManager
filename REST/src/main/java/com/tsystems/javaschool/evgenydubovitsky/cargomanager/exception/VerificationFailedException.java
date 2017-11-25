package com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception;

public class VerificationFailedException extends BusinessException {

    private static final int HTTP_CODE = 400;

    public VerificationFailedException(String message) {
        super(message);
    }

    @Override
    public int getHttpCode() {
        return HTTP_CODE;
    }
}
