package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Driver;

import java.sql.Timestamp;

public class DriverDTO extends DTO<Driver> {

    private String persNumber;
    private String firstName;
    private String lastName;
    private Driver.Status status;
    private Timestamp lastStatusUpdate;
    private long workedThisMonthMs;
    private CityDTO location;
    private VehicleDTO vehicle;

    /**
     * Empty
     */
    public DriverDTO() {
    }

    /**
     * Identifier
     */
    public DriverDTO(String persNumber) {
        this.persNumber = persNumber;
    }

    /**
     * Creator
     */
    public DriverDTO(String persNumber, String firstName, String lastName, CityDTO location) {
        this.persNumber = persNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
    }

    /**
     * Changer
     */
    public DriverDTO(String persNumber, String firstName, String lastName, CityDTO location, VehicleDTO vehicle, Driver.Status status) {
        this.persNumber = persNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.vehicle = vehicle;
        this.status = status;
    }

    /**
     * Full
     */
    public DriverDTO(long id, String persNumber, String firstName, String lastName, CityDTO location, VehicleDTO vehicle, Driver.Status status, Timestamp lastStatusUpdate, long workedThisMonthMs) {
        this.id = id;
        this.persNumber = persNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.vehicle = vehicle;
        this.status = status;
        this.lastStatusUpdate = lastStatusUpdate;
        this.workedThisMonthMs = workedThisMonthMs;
    }

    /**
     * Converter
     */
    public DriverDTO(Driver entity) {
        this(
                entity.getId(),
                entity.getPersNumber(),
                entity.getFirstName(),
                entity.getLastName(),
                null,
                null,
                entity.getStatus(),
                entity.getLastStatusUpdate(),
                entity.getWorkedThisMonthMs()
        );
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

    public long getWorkedThisMonthMs() {
        return workedThisMonthMs;
    }

    public void setWorkedThisMonthMs(long workedThisMonthMs) {
        this.workedThisMonthMs = workedThisMonthMs;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverDTO)) return false;
        if (!super.equals(o)) return false;

        DriverDTO driverDTO = (DriverDTO) o;

        if (workedThisMonthMs != driverDTO.workedThisMonthMs) return false;
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
        result = 31 * result + (int) (workedThisMonthMs ^ (workedThisMonthMs >>> 32));
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (vehicle != null ? vehicle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
                "id=" + id +
                ", persNumber='" + persNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", lastStatusUpdate=" + lastStatusUpdate +
                ", workedThisMonthMs=" + workedThisMonthMs +
                '}';
    }
}
