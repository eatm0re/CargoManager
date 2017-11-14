package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.CityDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import org.springframework.stereotype.Repository;

@Repository
public class CityDAOImpl extends AbstractDAO<City> implements CityDAO {

    public CityDAOImpl() {
        super(City.class);
    }
}
