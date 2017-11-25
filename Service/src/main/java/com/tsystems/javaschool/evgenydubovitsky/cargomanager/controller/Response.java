package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import java.io.Serializable;

public class Response implements Serializable {

    private final int code;
    private final String message;
    private final Serializable body;

    public Response(int code, String message, Object body) {
        this.code = code;
        this.message = message;
        if (body instanceof Serializable) {
            this.body = (Serializable) body;
        } else {
            throw new IllegalArgumentException("Response body is not serializable");
        }
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Serializable getBody() {
        return body;
    }
}
