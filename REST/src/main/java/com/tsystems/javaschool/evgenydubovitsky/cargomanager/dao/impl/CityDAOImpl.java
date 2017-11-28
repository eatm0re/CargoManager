package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.CityDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CityDAOImpl extends AbstractDAO<City> implements CityDAO {

    public CityDAOImpl() {
        super(City.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public City selectByName(String name) {
        List<City> list = selectByParam("name", name);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    @Loggable
    public boolean deleteByName(String name) {
        return deleteByParam("name", name) > 0;
    }
}
