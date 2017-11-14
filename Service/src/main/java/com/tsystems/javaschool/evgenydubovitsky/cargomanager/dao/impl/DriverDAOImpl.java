package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DriverDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;
import org.springframework.stereotype.Repository;

@Repository
public class DriverDAOImpl extends AbstractDAO<Driver> implements DriverDAO {

    public DriverDAOImpl() {
        super(Driver.class);
    }
}
