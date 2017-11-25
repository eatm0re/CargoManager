package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CargoDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;

public interface CargoService extends Service<CargoDTO> {

    long add(CargoDTO cargoDTO) throws BusinessException;

    void change(CargoDTO cargoDTO) throws BusinessException;
}
