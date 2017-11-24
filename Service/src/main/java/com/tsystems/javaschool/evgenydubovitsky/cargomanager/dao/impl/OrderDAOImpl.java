package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.OrderDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Checkpoint;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Order;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderDAOImpl extends AbstractDAO<Order> implements OrderDAO {

    public OrderDAOImpl() {
        super(Order.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void assignVehicle(Order order, Vehicle vehicle) {
        order.setVehicle(vehicle);
        vehicle.setOrder(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void unassignVehicle(Order order) {
        Vehicle vehicle = order.getVehicle();
        order.setVehicle(null);
        vehicle.setOrder(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void increaseProgress(Order order) {
        order.setProgress(order.getProgress() + 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void resetProgress(Order order) {
        order.setProgress(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void addCheckpoint(Order order, Checkpoint checkpoint) {
        checkpoint.setOrder(order);
        order.putCheckpoint(checkpoint);
    }
}
