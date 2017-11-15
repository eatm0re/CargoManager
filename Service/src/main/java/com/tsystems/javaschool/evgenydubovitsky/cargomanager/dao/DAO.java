package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.AbstractEntity;

import java.util.List;

public interface DAO<E extends AbstractEntity> {

    List<E> selectAll();

    E selectById(long id);

    List<E> selectByParam(String param, Object value);

    void insert(E obj);

    void update(E obj);

    void delete(long id);
}
