package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;

import java.sql.Timestamp;

public class DriverDTO extends DTO<Driver> {

    private String persNumber;
    private String firstName;
    private String lastName;
    private Driver.Status status;
    private Timestamp lastStatusUpdate;
    private int workedThisMonth;
    private CityDTO location;
    private VehicleDTO vehicle;

    public DriverDTO(long id, String persNumber, String firstName, String lastName, Driver.Status status, Timestamp lastStatusUpdate, int workedThisMonth) {
        this.id = id;
        this.persNumber = persNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.lastStatusUpdate = lastStatusUpdate;
        this.workedThisMonth = workedThisMonth;
    }

    public DriverDTO(long id, String persNumber, String firstName, String lastName, Driver.Status status, Timestamp lastStatusUpdate, int workedThisMonth, CityDTO location, VehicleDTO vehicle) {
        this(id, persNumber, firstName, lastName, status, lastStatusUpdate, workedThisMonth);
        this.location = location;
        this.vehicle = vehicle;
    }

    public DriverDTO(Driver entity) {
        this(entity.getId(), entity.getPersNumber(), entity.getFirstName(), entity.getLastName(), entity.getStatus(), entity.getLastStatusUpdate(), entity.getWorkedThisMonth());
    }

    @Override
    public void fill(Driver entity) {
        location = new CityDTO(entity.getLocation());
        vehicle = entity.getVehicle() == null ? null : new VehicleDTO(entity.getVehicle());
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

    public CityDTO getLocation() {
        return location;
    }

    public void setLocation(CityDTO location) {
        this.location = location;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverDTO)) return false;
        if (!super.equals(o)) return false;

        DriverDTO driverDTO = (DriverDTO) o;

        if (workedThisMonth != driverDTO.workedThisMonth) return false;
        if (persNumber != null ? !persNumber.equals(driverDTO.persNumber) : driverDTO.persNumber != null) return false;
        if (firstName != null ? !firstName.equals(driverDTO.firstName) : driverDTO.firstName != null) return false;
        if (lastName != null ? !lastName.equals(driverDTO.lastName) : driverDTO.lastName != null) return false;
        if (status != driverDTO.status) return false;
        if (lastStatusUpdate != null ? !lastStatusUpdate.equals(driverDTO.lastStatusUpdate) : driverDTO.lastStatusUpdate != null)
            return false;
        if (location != null ? !location.equals(driverDTO.location) : driverDTO.location != null) return false;
        return vehicle != null ? vehicle.equals(driverDTO.vehicle) : driverDTO.vehicle == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (persNumber != null ? persNumber.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (lastStatusUpdate != null ? lastStatusUpdate.hashCode() : 0);
        result = 31 * result + workedThisMonth;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (vehicle != null ? vehicle.hashCode() : 0);
        return result;
    }
}
