package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "vehicles", schema = "cargomanager")
public class Vehicle implements Serializable {

    public enum Status {OK, BROKEN}

    private long id;
    private String regNumber;
    private long capacityKg;
    private Status status = Status.OK;
    private List<Driver> drivers;
    private Order order;
    private City location;

    @Id
    @Column(name = "idVehicle", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "vehicleRegNumber", nullable = false, length = 45)
    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    @Basic
    @Column(name = "vehicleCapacityKg", nullable = false)
    public long getCapacityKg() {
        return capacityKg;
    }

    public void setCapacityKg(long capacityKg) {
        this.capacityKg = capacityKg;
    }

    @Basic
    @Column(name = "vehicleStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "vehicle")
    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @OneToOne(mappedBy = "vehicle")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "vehicleCityId", referencedColumnName = "idCity", nullable = false)
    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (id != vehicle.id) return false;
        if (capacityKg != vehicle.capacityKg) return false;
        if (regNumber != null ? !regNumber.equals(vehicle.regNumber) : vehicle.regNumber != null) return false;
        return status != null ? status.equals(vehicle.status) : vehicle.status == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (regNumber != null ? regNumber.hashCode() : 0);
        result = 31 * result + (int) (capacityKg ^ (capacityKg >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", regNumber='" + regNumber + '\'' +
                '}';
    }
}
