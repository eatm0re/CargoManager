package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Order;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;

import java.util.List;

public class VehicleDTO extends DTO {

    private String regNumber;
    private long capacityKg;
    private Vehicle.Status status;
    private List<Driver> drivers;
    private Order order;
    private City location;

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public long getCapacityKg() {
        return capacityKg;
    }

    public void setCapacityKg(long capacityKg) {
        this.capacityKg = capacityKg;
    }

    public Vehicle.Status getStatus() {
        return status;
    }

    public void setStatus(Vehicle.Status status) {
        this.status = status;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "id=" + id +
                ", regNumber='" + regNumber + '\'' +
                '}';
    }
}
