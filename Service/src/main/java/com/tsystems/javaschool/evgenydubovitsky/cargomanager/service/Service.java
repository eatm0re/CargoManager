package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DTO;

import java.util.List;

public interface Service<E extends DTO> {

    List<E> getAll();

    E findById(long id);

    List<E> findByParam(String param, Object value);

    E add(E obj);

    E change(E obj);

    void removeById(long id);
}
