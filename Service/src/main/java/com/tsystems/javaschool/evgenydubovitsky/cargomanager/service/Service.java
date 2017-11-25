package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;

import java.util.List;

public interface Service<E extends DTO<?>> {

    List<E> getAll() throws BusinessException;

    E findById(long id) throws BusinessException;
}
