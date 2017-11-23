package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.VehicleDTO;

public interface VehicleService extends Service<VehicleDTO> {

    VehicleDTO findByRegNumber(String regNumber);

    long add(VehicleDTO vehicleDTO);

    void change(VehicleDTO vehicleDTO);
}
