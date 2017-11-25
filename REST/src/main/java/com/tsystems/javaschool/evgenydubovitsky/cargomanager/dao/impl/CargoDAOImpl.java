package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.CargoDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;
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

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    @Loggable
    public void load(Cargo cargo, Vehicle vehicle) {
        cargo.setVehicle(vehicle);
        vehicle.putCargo(cargo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    @Loggable
    public void unload(Cargo cargo) {
        cargo.getVehicle().removeCargo(cargo);
        cargo.setVehicle(null);
    }
}
