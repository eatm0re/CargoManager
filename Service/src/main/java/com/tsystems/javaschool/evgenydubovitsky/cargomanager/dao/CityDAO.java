package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;

public interface CityDAO extends DAO<City> {

    City selectByName(String name);
}
