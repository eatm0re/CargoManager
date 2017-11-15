package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import java.io.Serializable;

public abstract class DTO implements Serializable {

    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
