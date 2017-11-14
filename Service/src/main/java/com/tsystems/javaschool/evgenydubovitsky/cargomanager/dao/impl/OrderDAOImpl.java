package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.OrderDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl extends AbstractDAO<Order> implements OrderDAO {

    public OrderDAOImpl() {
        super(Order.class);
    }
}
