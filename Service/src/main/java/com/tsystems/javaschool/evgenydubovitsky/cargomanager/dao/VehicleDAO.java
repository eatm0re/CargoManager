package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;

public interface VehicleDAO extends DAO<Vehicle> {

    Vehicle selectByRegNumber(String regNumber);

    void move(Vehicle vehicle, City location);
}
