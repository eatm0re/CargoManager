package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.VehicleDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDAOImpl extends AbstractDAO<Vehicle> implements VehicleDAO {

    public VehicleDAOImpl() {
        super(Vehicle.class);
    }
}
