package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.LazyListProxy;
import org.hibernate.collection.spi.PersistentCollection;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "vehicles", schema = "cargomanager")
public class Vehicle extends AbstractEntity {

    public enum Status {OK, BROKEN}

    private String regNumber;
    private long capacityKg;
    private Status status = Status.OK;
    private List<Driver> drivers;
    private List<Cargo> cargoes;
    private Order order;
    private City location;

    private boolean driversProxied;
    private boolean cargoesProxied;

    public Vehicle() {
    }

    public Vehicle(long id, String regNumber, long capacityKg, Status status, List<Driver> drivers, Order order, City location) {
        this.id = id;
        this.regNumber = regNumber;
        this.capacityKg = capacityKg;
        this.status = status;
        this.drivers = drivers;
        this.order = order;
        this.location = location;
    }

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

    public void putDriver(Driver driver) {
        if (drivers == null) {
            drivers = new LinkedList<>();
        }
        drivers.add(driver);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeDriver(Driver driver) {
        if (!driversProxied && drivers instanceof PersistentCollection) {
            drivers = new LazyListProxy<>(drivers);
            driversProxied = true;
        }
        return drivers.remove(driver);
    }

    @OneToMany(mappedBy = "vehicle")
    public List<Cargo> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<Cargo> cargoes) {
        this.cargoes = cargoes;
    }

    public void putCargo(Cargo cargo) {
        if (cargoes == null) {
            cargoes = new LinkedList<>();
        }
        cargoes.add(cargo);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean removeCargo(Cargo cargo) {
        if (!cargoesProxied && cargoes instanceof PersistentCollection) {
            cargoes = new LazyListProxy<>(cargoes);
            cargoesProxied = true;
        }
        return cargoes.remove(cargo);
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
                ", capacityKg=" + capacityKg +
                ", status=" + status +
                '}';
    }
}
