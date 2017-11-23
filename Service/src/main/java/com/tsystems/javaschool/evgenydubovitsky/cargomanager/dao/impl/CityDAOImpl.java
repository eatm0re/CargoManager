package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.CityDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityDAOImpl extends AbstractDAO<City> implements CityDAO {

    public CityDAOImpl() {
        super(City.class);
    }

    @Override
    public City selectByName(String name) {
        List<City> list = selectByParam("name", name);
        return list.size() == 0 ? null : list.get(0);
    }
}
