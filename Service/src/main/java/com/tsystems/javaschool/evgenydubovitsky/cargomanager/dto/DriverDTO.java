package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;

import java.io.Serializable;
import java.sql.Timestamp;

public class DriverDTO implements Serializable {

    private long id;
    private String persNumber;
    private String firstName;
    private String lastName;
    private Driver.Status status;
    private Timestamp lastStatusUpdate;
    private int workedThisMonth;
    private City location;
    private Vehicle vehicle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPersNumber() {
        return persNumber;
    }

    public void setPersNumber(String persNumber) {
        this.persNumber = persNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Driver.Status getStatus() {
        return status;
    }

    public void setStatus(Driver.Status status) {
        this.status = status;
    }

    public Timestamp getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public void setLastStatusUpdate(Timestamp lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }

    public int getWorkedThisMonth() {
        return workedThisMonth;
    }

    public void setWorkedThisMonth(int workedThisMonth) {
        this.workedThisMonth = workedThisMonth;
    }

    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
                "id=" + id +
                ", persNumber='" + persNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
