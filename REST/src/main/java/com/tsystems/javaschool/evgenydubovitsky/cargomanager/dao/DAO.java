package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.AbstractEntity;

import java.util.List;

public interface DAO<E extends AbstractEntity> {

    Class<E> getEntityClass();

    List<E> selectAll();

    E selectById(long id);

    void insert(E obj);

    void update(E obj);

    boolean deleteById(long id);
}
