package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cargoes", schema = "cargomanager")
public class Cargo {
    private long id;
    private String name;
    private BigDecimal weightKg;
    private Serializable status;
    private City location;
    private List<Task> tasks;

    @Id
    @Column(name = "idCargo", nullable = false)
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
    public Serializable getStatus() {
        return status;
    }

    public void setStatus(Serializable status) {
        this.status = status;
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

    @ManyToOne
    @JoinColumn(name = "cargoCityId", referencedColumnName = "idCity", nullable = false)
    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    @OneToMany(mappedBy = "cargo")
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
