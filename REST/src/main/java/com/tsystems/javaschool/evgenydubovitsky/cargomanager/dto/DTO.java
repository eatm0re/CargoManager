package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.AbstractEntity;

import java.io.Serializable;

public abstract class DTO<E extends AbstractEntity> implements Serializable {

    protected long id;

    public abstract void fill(E entity);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DTO)) return false;

        DTO<?> dto = (DTO<?>) o;

        return id == dto.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
