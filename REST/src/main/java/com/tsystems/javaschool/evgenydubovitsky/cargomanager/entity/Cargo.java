package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cargoes", schema = "cargomanager")
public class Cargo extends AbstractEntity {

    public enum Status {READY, SHIPPED, DELIVERED}

    private String name;
    private BigDecimal weightKg = BigDecimal.ZERO;
    private Status status = Status.READY;
    private City location;
    private Vehicle vehicle;

    public Cargo() {
    }

    public Cargo(long id, String name, BigDecimal weightKg, Status status) {
        this.id = id;
        this.name = name;
        this.weightKg = weightKg;
        this.status = status;
    }

    @Id
    @Column(name = "idCargo", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cargoName", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "cargoWeightKg", nullable = false, precision = 3)
    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    @Basic
    @Column(name = "cargoStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "cargoCityId", referencedColumnName = "idCity", nullable = false)
    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    @ManyToOne
    @JoinColumn(name = "cargoVehicleId", referencedColumnName = "idVehicle")
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

        Cargo cargo = (Cargo) o;

        if (id != cargo.id) return false;
        if (name != null ? !name.equals(cargo.name) : cargo.name != null) return false;
        if (weightKg != null ? !weightKg.equals(cargo.weightKg) : cargo.weightKg != null) return false;
        return status != null ? status.equals(cargo.status) : cargo.status == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (weightKg != null ? weightKg.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weightKg=" + weightKg +
                ", status=" + status +
                '}';
    }
}
