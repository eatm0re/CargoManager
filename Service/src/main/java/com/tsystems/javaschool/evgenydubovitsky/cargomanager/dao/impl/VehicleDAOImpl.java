package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.VehicleDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleDAOImpl extends AbstractDAO<Vehicle> implements VehicleDAO {

    public VehicleDAOImpl() {
        super(Vehicle.class);
    }

    @Override
    public Vehicle selectByRegNumber(String regNumber) {
        List<Vehicle> list = selectByParam("regNumber", regNumber);
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public void move(Vehicle vehicle, City location) {
        City prevLocation = vehicle.getLocation();
        vehicle.setLocation(location);

        prevLocation.removeVehicle(vehicle);
        location.putVehicle(vehicle);
    }
}
