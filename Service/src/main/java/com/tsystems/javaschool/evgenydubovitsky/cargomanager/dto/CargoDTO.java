package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Task;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CargoDTO extends DTO<Cargo> {

    private String name;
    private BigDecimal weightKg;
    private Cargo.Status status;
    private CityDTO location;
    private List<TaskDTO> tasks;

    public CargoDTO(long id, String name, BigDecimal weightKg, Cargo.Status status) {
        this.id = id;
        this.name = name;
        this.weightKg = weightKg;
        this.status = status;
    }

    public CargoDTO(long id, String name, BigDecimal weightKg, Cargo.Status status, CityDTO location, List<TaskDTO> tasks) {
        this(id, name, weightKg, status);
        this.location = location;
        this.tasks = tasks;
    }

    public CargoDTO(Cargo entity) {
        this(entity.getId(), entity.getName(), entity.getWeightKg(), entity.getStatus());
    }

    @Override
    public void fill(Cargo entity) {
        City location = entity.getLocation();
        this.location = new CityDTO(location);

        List<Task> tasks = entity.getTasks();
        this.tasks = tasks.stream().map(TaskDTO::new).collect(Collectors.toList());
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

    public CityDTO getLocation() {
        return location;
    }

    public void setLocation(CityDTO location) {
        this.location = location;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CargoDTO)) return false;
        if (!super.equals(o)) return false;

        CargoDTO cargoDTO = (CargoDTO) o;

        if (name != null ? !name.equals(cargoDTO.name) : cargoDTO.name != null) return false;
        if (weightKg != null ? !weightKg.equals(cargoDTO.weightKg) : cargoDTO.weightKg != null) return false;
        if (status != cargoDTO.status) return false;
        if (location != null ? !location.equals(cargoDTO.location) : cargoDTO.location != null) return false;
        return tasks != null ? tasks.equals(cargoDTO.tasks) : cargoDTO.tasks == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (weightKg != null ? weightKg.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }
}
