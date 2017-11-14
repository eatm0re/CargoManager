package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Task;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CargoDTO implements Serializable {

    private long id;
    private String name;
    private BigDecimal weightKg;
    private Cargo.Status status;
    private City location;
    private List<Task> tasks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public Cargo.Status getStatus() {
        return status;
    }

    public void setStatus(Cargo.Status status) {
        this.status = status;
    }

    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "CargoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weightKg=" + weightKg +
                '}';
    }
}
