package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.OrderDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;

public interface OrderService extends Service<OrderDTO> {

    long add(OrderDTO orderDTO) throws BusinessException;

    void progressReport(long id) throws BusinessException;

    void interrupt(long id) throws BusinessException;

    long capacityNeeded(long id) throws BusinessException;

    long timeNeeded(long id) throws BusinessException;
}
