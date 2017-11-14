package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "drivers", schema = "cargomanager")
public class Driver implements Serializable {

    public enum Status {REST, WORK}

    private long id;
    private String persNumber;
    private String firstName;
    private String lastName;
    private Status status = Status.REST;
    private Timestamp lastStatusUpdate = Timestamp.valueOf(LocalDateTime.now());
    private int workedThisMonth;
    private City location;
    private Vehicle vehicle;

    @Id
    @Column(name = "idDriver", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "driverPersNumber", nullable = false, length = 45)
    public String getPersNumber() {
        return persNumber;
    }

    public void setPersNumber(String persNumber) {
        this.persNumber = persNumber;
    }

    @Basic
    @Column(name = "driverFirstName", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "driverLastName", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "driverStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Basic
    @Column(name = "driverLastStatusUpdate", nullable = false)
    public Timestamp getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public void setLastStatusUpdate(Timestamp lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }

    @Basic
    @Column(name = "driverWorkedThisMonth", nullable = false)
    public int getWorkedThisMonth() {
        return workedThisMonth;
    }

    public void setWorkedThisMonth(int workedThisMonth) {
        this.workedThisMonth = workedThisMonth;
    }

    @ManyToOne
    @JoinColumn(name = "driverCityId", referencedColumnName = "idCity", nullable = false)
    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    @ManyToOne
    @JoinColumn(name = "driverVehicleId", referencedColumnName = "idVehicle")
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        if (id != driver.id) return false;
        if (workedThisMonth != driver.workedThisMonth) return false;
        if (persNumber != null ? !persNumber.equals(driver.persNumber) : driver.persNumber != null) return false;
        if (firstName != null ? !firstName.equals(driver.firstName) : driver.firstName != null) return false;
        if (lastName != null ? !lastName.equals(driver.lastName) : driver.lastName != null) return false;
        if (status != null ? !status.equals(driver.status) : driver.status != null) return false;
        return lastStatusUpdate != null ? lastStatusUpdate.equals(driver.lastStatusUpdate) : driver.lastStatusUpdate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (persNumber != null ? persNumber.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (lastStatusUpdate != null ? lastStatusUpdate.hashCode() : 0);
        result = 31 * result + workedThisMonth;
        return result;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", persNumber='" + persNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
