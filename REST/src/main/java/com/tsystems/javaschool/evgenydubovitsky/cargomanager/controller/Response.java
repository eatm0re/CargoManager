package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import org.hibernate.type.SerializationException;

import java.io.Serializable;

public class Response implements Serializable {

    public static final Response OK = new Response(200, "OK");

    private final int code;
    private final String message;
    private final Serializable body;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.body = null;
    }

    public Response(int code, String message, Object body) {
        this.code = code;
        this.message = message;
        try {
            this.body = (Serializable) body;
        } catch (ClassCastException e) {
            throw new SerializationException("Response body is not serializable", null);
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

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", body=" + body +
                '}';
    }
}
