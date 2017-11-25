package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Checkpoint;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Order;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;

public interface OrderDAO extends DAO<Order> {

    void assignVehicle(Order order, Vehicle vehicle);

    void unassignVehicle(Order order);

    void increaseProgress(Order order);

    void resetProgress(Order order);

    void addCheckpoint(Order order, Checkpoint checkpoint);
}
