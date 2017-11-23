package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DTO;

import java.util.List;

public interface Service<E extends DTO<?>> {

    List<E> getAll();

    E findById(long id);

    //List<E> findByParam(String param, Object value);

    void removeByParam(String param, Object value);
}
