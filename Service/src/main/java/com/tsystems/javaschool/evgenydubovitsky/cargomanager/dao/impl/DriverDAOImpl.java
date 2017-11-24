package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DriverDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DriverDAOImpl extends AbstractDAO<Driver> implements DriverDAO {

    public DriverDAOImpl() {
        super(Driver.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    public Driver selectByPersNumber(String persNumber) {
        List<Driver> list = selectByParam("persNumber", persNumber);
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void move(Driver driver, City location) {
        City prevLocation = driver.getLocation();
        driver.setLocation(location);

        prevLocation.removeDriver(driver);
        location.putDriver(driver);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void bind(Driver driver, Vehicle vehicle) {
        Vehicle prevVehicle = driver.getVehicle();
        driver.setVehicle(vehicle);

        if (prevVehicle != null) {
            prevVehicle.removeDriver(driver);
        }
        vehicle.putDriver(driver);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void unbind(Driver driver) {
        Vehicle vehicle = driver.getVehicle();
        driver.setVehicle(null);
        vehicle.removeDriver(driver);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void updateStatus(Driver driver, Driver.Status status) {
        if (status == driver.getStatus()) {
            return;
        }

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        if (status == Driver.Status.REST) {
            long diff = now.getTime() - driver.getLastStatusUpdate().getTime();
            driver.setWorkedThisMonthMs(driver.getWorkedThisMonthMs() + diff + 1);
            driver.setStatus(Driver.Status.REST);
        } else {
            driver.setStatus(Driver.Status.WORK);
            driver.setLastStatusUpdate(now);
        }
    }
}
