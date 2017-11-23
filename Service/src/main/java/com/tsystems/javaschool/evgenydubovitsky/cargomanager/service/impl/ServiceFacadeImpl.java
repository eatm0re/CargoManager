package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFacadeImpl implements ServiceFacade {

    private CargoService cargoService;
    private CheckpointService checkpointService;
    private CityService cityService;
    private DriverService driverService;
    private OrderService orderService;
    private TaskService taskService;
    private UserService userService;
    private VehicleService vehicleService;

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setCheckpointService(CheckpointService checkpointService) {
        this.checkpointService = checkpointService;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public CargoService getCargoService() {
        return cargoService;
    }

    @Override
    public CheckpointService getCheckpointService() {
        return checkpointService;
    }

    @Override
    public CityService getCityService() {
        return cityService;
    }

    @Override
    public DriverService getDriverService() {
        return driverService;
    }

    @Override
    public OrderService getOrderService() {
        return orderService;
    }

    @Override
    public TaskService getTaskService() {
        return taskService;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public VehicleService getVehicleService() {
        return vehicleService;
    }
}
