package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DriverDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverDAOImpl extends AbstractDAO<Driver> implements DriverDAO {

    public DriverDAOImpl() {
        super(Driver.class);
    }

    @Override
    public Driver selectByPersNumber(String persNumber) {
        List<Driver> list = selectByParam("persNumber", persNumber);
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public void move(Driver driver, City location) {
        City prevLocation = driver.getLocation();
        driver.setLocation(location);

        prevLocation.removeDriver(driver);
        location.putDriver(driver);
    }

    @Override
    public void bind(Driver driver, Vehicle vehicle) {
        Vehicle prevVehicle = driver.getVehicle();
        driver.setVehicle(vehicle);

        if (prevVehicle != null) {
            prevVehicle.removeDriver(driver);
        }
        vehicle.putDriver(driver);
    }

    @Override
    public void unbind(Driver driver) {
        Vehicle vehicle = driver.getVehicle();
        driver.setVehicle(null);
        vehicle.removeDriver(driver);
    }
}
