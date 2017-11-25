package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;

public interface DriverService extends Service<DriverDTO> {

    DriverDTO findByPersNumber(String persNumber) throws BusinessException;

    long add(DriverDTO driverDTO) throws BusinessException;

    void change(DriverDTO driverDTO) throws BusinessException;
}
