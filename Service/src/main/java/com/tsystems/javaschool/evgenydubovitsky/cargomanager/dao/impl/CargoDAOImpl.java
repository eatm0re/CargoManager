package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.CargoDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import org.springframework.stereotype.Repository;

@Repository
public class CargoDAOImpl extends AbstractDAO<Cargo> implements CargoDAO {

    public CargoDAOImpl() {
        super(Cargo.class);
    }

    @Override
    public void move(Cargo cargo, City location) {
        cargo.setLocation(location);
    }
}
