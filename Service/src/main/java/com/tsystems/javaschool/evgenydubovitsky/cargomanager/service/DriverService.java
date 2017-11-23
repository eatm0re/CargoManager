package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;

public interface DriverService extends Service<DriverDTO> {

    DriverDTO findByPersNumber(String persNumber);

    long add(DriverDTO driverDTO);

    void change(DriverDTO driverDTO);
}
