package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.CargoDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CargoDAOImpl extends AbstractDAO<Cargo> implements CargoDAO {

    public CargoDAOImpl() {
        super(Cargo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    @Loggable
    public void move(Cargo cargo, City location) {
        cargo.setLocation(location);
    }
}
