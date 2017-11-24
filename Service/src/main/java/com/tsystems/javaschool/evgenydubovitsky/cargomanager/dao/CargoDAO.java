package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;

public interface CargoDAO extends DAO<Cargo> {

    void move(Cargo cargo, City location);

    void load(Cargo cargo, Vehicle vehicle);

    void unload(Cargo cargo);
}
