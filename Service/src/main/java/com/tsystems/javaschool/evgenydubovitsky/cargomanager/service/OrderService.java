package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.OrderDTO;

public interface OrderService extends Service<OrderDTO> {

    long add(OrderDTO orderDTO);

    void progressReport(long id);

    void interrupt(long id);

    long capacityNeeded(long id);
}
