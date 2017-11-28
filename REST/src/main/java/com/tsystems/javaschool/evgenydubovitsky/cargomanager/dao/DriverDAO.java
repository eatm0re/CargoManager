package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;

import java.util.Collection;

public interface DriverDAO extends DAO<Driver> {

    Driver selectByPersNumber(String persNumber);

    boolean deleteByPersNumber(String persNumber);

    void move(Driver driver, City location);

    void moveAll(Collection<Driver> drivers, City location);

    void bind(Driver driver, Vehicle vehicle);

    void unbind(Driver driver);

    void updateStatus(Driver driver, Driver.Status status);
}
