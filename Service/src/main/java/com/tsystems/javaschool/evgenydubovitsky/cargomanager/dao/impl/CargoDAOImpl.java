package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.CargoDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import org.springframework.stereotype.Repository;

@Repository
public class CargoDAOImpl extends AbstractDAO<Cargo> implements CargoDAO {

    public CargoDAOImpl() {
        super(Cargo.class);
    }
}
