package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CargoDTO;

public interface CargoService extends Service<CargoDTO> {

    long add(CargoDTO cargoDTO);

    void change(CargoDTO cargoDTO);
}
