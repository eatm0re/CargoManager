package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;

import java.math.BigDecimal;

public class CargoDTO extends DTO<Cargo> {

    private String name;
    private BigDecimal weightKg;
    private CityDTO location;
    private Cargo.Status status;

    /**
     * Identifier
     */
    public CargoDTO(long id) {
        this.id = id;
    }

    /**
     * Creator
     */
    public CargoDTO(String name, BigDecimal weightKg, CityDTO location) {
        this.name = name;
        this.weightKg = weightKg;
        this.location = location;
    }

    /**
     * Changer
     */
    public CargoDTO(long id, String name, BigDecimal weightKg) {
        this.id = id;
        this.name = name;
        this.weightKg = weightKg;
    }

    /**
     * Full
     */
    public CargoDTO(long id, String name, BigDecimal weightKg, CityDTO location, Cargo.Status status) {
        this.id = id;
        this.name = name;
        this.weightKg = weightKg;
        this.location = location;
        this.status = status;
    }

    /**
     * Converter
     */
    public CargoDTO(Cargo entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getWeightKg(),
                null,
                entity.getStatus()
        );
    }

    @Override
    public void fill(Cargo entity) {
        City location = entity.getLocation();
        this.location = new CityDTO(location);
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
        return location != null ? location.equals(cargoDTO.location) : cargoDTO.location == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (weightKg != null ? weightKg.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
