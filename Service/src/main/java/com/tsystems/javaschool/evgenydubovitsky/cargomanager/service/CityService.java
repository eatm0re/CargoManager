package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;

public interface CityService extends Service<CityDTO> {

    long add(CityDTO cityDTO);

    void change(CityDTO cityDTO);
}
