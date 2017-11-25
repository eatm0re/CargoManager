package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;

public interface VehicleDAO extends DAO<Vehicle> {

    Vehicle selectByRegNumber(String regNumber);

    void move(Vehicle vehicle, City location);
}
