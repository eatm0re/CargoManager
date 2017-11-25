package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;

public interface CargoDAO extends DAO<Cargo> {

    void move(Cargo cargo, City location);

    void load(Cargo cargo, Vehicle vehicle);

    void unload(Cargo cargo);
}
