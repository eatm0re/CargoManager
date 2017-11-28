package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.*;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.CityService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.Math.*;

@Service
public class CityServiceImpl extends AbstractService<City, CityDTO> implements CityService {

    private static final double EARTH_RADIUS = 6371.0;
    private static final double DISTANCE_MULTIPLIER = 1.15;

    public CityServiceImpl() {
        super(City.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public CityDTO findByName(String name) throws BusinessException {
        if (!isSimpleName(name)) {
            throw new WrongParameterException("Wrong city name");
        }
        City city = dao.getCityDAO().selectByName(name);
        if (city == null) {
            throw new EntityNotFoundException("City " + name + " not found");
        }

        CityDTO res = new CityDTO(city);
        res.fill(city);
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public long add(CityDTO cityDTO) throws BusinessException {

        // creating city
        City city = new City();

        // name
        String cityName = cityDTO.getName();
        if (cityName == null || cityName.length() == 0) {
            throw new MissedParameterException("City name must be specified");
        }
        if (!isSimpleName(cityName)) {
            throw new WrongParameterException("Wrong city name");
        }
        if (dao.getCityDAO().selectByName(cityName) != null) {
            throw new EntityExistsException("City " + cityName + " is already exists");
        }
        city.setName(cityName);

        // latitude
        double latitude = cityDTO.getLatitude();
        if (latitude > 90 || latitude < -90) {
            throw new WrongParameterException("City latitude must be between -90 and +90");
        }
        city.setLatitude(latitude);

        // longitude
        double longitude = cityDTO.getLongitude();
        if (longitude > 180 || longitude <= -180) {
            throw new WrongParameterException("City longitude must be between -180 (not including) and +180 (including)");
        }
        city.setLongitude(longitude);

        // insert
        dao.getCityDAO().insert(city);
        return city.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public void change(CityDTO cityDTO) throws BusinessException {

        // find city by name
        String cityName = cityDTO.getName();
        if (cityName == null || cityName.length() == 0) {
            throw new MissedParameterException("City name must be specified");
        }
        if (!isSimpleName(cityName)) {
            throw new WrongParameterException("Wrong city name");
        }
        City city = dao.getCityDAO().selectByName(cityName);
        if (city == null) {
            throw new EntityNotFoundException("City " + cityName + " not found");
        }

        // latitude
        double latitude = cityDTO.getLatitude();
        if (latitude > 90 || latitude < -90) {
            throw new WrongParameterException("City latitude must be between -90 and +90");
        }
        city.setLatitude(latitude);

        // longitude
        double longitude = cityDTO.getLongitude();
        if (longitude > 180 || longitude <= -180) {
            throw new WrongParameterException("City longitude must be between -180 (not including) and +180 (including)");
        }
        city.setLongitude(longitude);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public double distance(String firstCityName, String secondCityName) throws BusinessException {
        if (firstCityName.equals(secondCityName)) {
            return 0.0;
        }
        if (!isSimpleName(firstCityName) || !isSimpleName(secondCityName)) {
            throw new WrongParameterException("Wrong city name");
        }
        City firstCity = dao.getCityDAO().selectByName(firstCityName);
        if (firstCity == null) {
            throw new EntityNotFoundException("City " + firstCityName + " not found");
        }
        City secondCity = dao.getCityDAO().selectByName(secondCityName);
        if (secondCity == null) {
            throw new EntityNotFoundException("City " + secondCityName + " not found");
        }
        return DISTANCE_MULTIPLIER * EARTH_RADIUS * acos(
                sin(toRadians(firstCity.getLatitude())) * sin(toRadians(secondCity.getLatitude())) +
                        cos(toRadians(firstCity.getLatitude())) * cos(toRadians(secondCity.getLatitude())) *
                                cos(toRadians(firstCity.getLongitude() - secondCity.getLongitude()))
        );
    }
}
