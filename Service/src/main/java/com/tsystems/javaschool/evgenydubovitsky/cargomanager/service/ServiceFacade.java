package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

public interface ServiceFacade {

    CargoService getCargoService();

    CheckpointService getCheckpointService();

    CityService getCityService();

    DriverService getDriverService();

    OrderService getOrderService();

    TaskService getTaskService();

    UserService getUserService();

    VehicleService getVehicleService();
}
