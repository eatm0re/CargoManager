package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;

public interface CityService extends Service<CityDTO> {

    long add(CityDTO cityDTO) throws BusinessException;

    void change(CityDTO cityDTO) throws BusinessException;

    double distance(String firstCityName, String secondCityName) throws BusinessException;
}
