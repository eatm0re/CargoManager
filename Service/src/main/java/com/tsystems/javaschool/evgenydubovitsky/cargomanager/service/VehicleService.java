package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.VehicleDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;

public interface VehicleService extends Service<VehicleDTO> {

    VehicleDTO findByRegNumber(String regNumber) throws BusinessException;

    long add(VehicleDTO vehicleDTO) throws BusinessException;

    void change(VehicleDTO vehicleDTO) throws BusinessException;
}
