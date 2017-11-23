package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.CityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
public class CityServiceImpl extends AbstractService<City, CityDTO> implements CityService {

    public CityServiceImpl() {
        super(City.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long add(CityDTO cityDTO) {

        // creating city
        City city = new City();

        // name
        String cityName = cityDTO.getName();
        if (cityName == null || cityName.length() == 0) {
            throw new IllegalArgumentException("City name must be specified");
        }
        if (!isSimpleName(cityName)) {
            throw new IllegalArgumentException("Wrong city name");
        }
        if (dao.getCityDAO().selectByName(cityName) != null) {
            throw new EntityExistsException("City " + cityName + " is already exists");
        }
        city.setName(cityName);

        // latitude
        double latitude = cityDTO.getLatitude();
        if (latitude > 90 || latitude < -90) {
            throw new IllegalArgumentException("City latitude must be between -90 and +90");
        }
        city.setLatitude(latitude);

        // longitude
        double longitude = cityDTO.getLongitude();
        if (longitude > 180 || longitude <= -180) {
            throw new IllegalArgumentException("City longitude must be between -180 (not including) and +180 (including)");
        }
        city.setLongitude(longitude);

        // insert
        dao.getCityDAO().insert(city);
        return city.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void change(CityDTO cityDTO) {

        // find city by name
        String cityName = cityDTO.getName();
        if (cityName == null || cityName.length() == 0) {
            throw new IllegalArgumentException("City name must be specified");
        }
        if (!isSimpleName(cityName)) {
            throw new IllegalArgumentException("Wrong city name");
        }
        City city = dao.getCityDAO().selectByName(cityName);
        if (city == null) {
            throw new EntityNotFoundException("City " + cityName + " not found");
        }

        // latitude
        double latitude = cityDTO.getLatitude();
        if (latitude > 90 || latitude < -90) {
            throw new IllegalArgumentException("City latitude must be between -90 and +90");
        }
        city.setLatitude(latitude);

        // longitude
        double longitude = cityDTO.getLongitude();
        if (longitude > 180 || longitude <= -180) {
            throw new IllegalArgumentException("City longitude must be between -180 (not including) and +180 (including)");
        }
        city.setLongitude(longitude);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    public double distance(String firstCityName, String secondCityName) {
        return 0;
    }
}
