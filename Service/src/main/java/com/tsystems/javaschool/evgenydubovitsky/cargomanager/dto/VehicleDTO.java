package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleDTO extends DTO<Vehicle> {

    private String regNumber;
    private long capacityKg;
    private Vehicle.Status status;
    private CityDTO location;
    private OrderDTO order;
    private List<DriverDTO> drivers;
    private List<CargoDTO> cargoes;

    /**
     * Identifier
     */
    public VehicleDTO(String regNumber) {
        this.regNumber = regNumber;
    }

    /**
     * Creator
     */
    public VehicleDTO(String regNumber, long capacityKg, CityDTO location) {
        this.regNumber = regNumber;
        this.capacityKg = capacityKg;
        this.location = location;
    }

    /**
     * Changer
     */
    public VehicleDTO(String regNumber, long capacityKg, Vehicle.Status status, CityDTO location, OrderDTO order) {
        this.regNumber = regNumber;
        this.capacityKg = capacityKg;
        this.status = status;
        this.location = location;
        this.order = order;
    }

    /**
     * Full
     */
    public VehicleDTO(long id, String regNumber, long capacityKg, Vehicle.Status status, CityDTO location, OrderDTO order, List<DriverDTO> drivers, List<CargoDTO> cargoes) {
        this.id = id;
        this.regNumber = regNumber;
        this.capacityKg = capacityKg;
        this.status = status;
        this.location = location;
        this.order = order;
        this.drivers = drivers;
        this.cargoes = cargoes;
    }

    /**
     * Converter
     */
    public VehicleDTO(Vehicle entity) {
        this(
                entity.getId(),
                entity.getRegNumber(),
                entity.getCapacityKg(),
                entity.getStatus(),
                null,
                null,
                null,
                null
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fill(Vehicle entity) {
        List<Driver> entityDrivers = entity.getDrivers();
        if (entityDrivers == null) {
            drivers = Collections.emptyList();
        } else {
            drivers = entityDrivers.stream().map(DriverDTO::new).collect(Collectors.toList());
        }

        List<Cargo> entityCargoes = entity.getCargoes();
        if (entityCargoes == null) {
            cargoes = Collections.emptyList();
        } else {
            cargoes = entityCargoes.stream().map(CargoDTO::new).collect(Collectors.toList());
        }

        order = entity.getOrder() == null ? null : new OrderDTO(entity.getOrder());
        location = new CityDTO(entity.getLocation());
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public long getCapacityKg() {
        return capacityKg;
    }

    public void setCapacityKg(long capacityKg) {
        this.capacityKg = capacityKg;
    }

    public Vehicle.Status getStatus() {
        return status;
    }

    public void setStatus(Vehicle.Status status) {
        this.status = status;
    }

    public List<DriverDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDTO> drivers) {
        this.drivers = drivers;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public CityDTO getLocation() {
        return location;
    }

    public void setLocation(CityDTO location) {
        this.location = location;
    }

    public List<CargoDTO> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<CargoDTO> cargoes) {
        this.cargoes = cargoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleDTO)) return false;
        if (!super.equals(o)) return false;

        VehicleDTO that = (VehicleDTO) o;

        if (capacityKg != that.capacityKg) return false;
        if (regNumber != null ? !regNumber.equals(that.regNumber) : that.regNumber != null) return false;
        if (status != that.status) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (drivers != null ? !drivers.equals(that.drivers) : that.drivers != null) return false;
        return cargoes != null ? cargoes.equals(that.cargoes) : that.cargoes == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (regNumber != null ? regNumber.hashCode() : 0);
        result = 31 * result + (int) (capacityKg ^ (capacityKg >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (drivers != null ? drivers.hashCode() : 0);
        result = 31 * result + (cargoes != null ? cargoes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "id=" + id +
                ", regNumber='" + regNumber + '\'' +
                ", capacityKg=" + capacityKg +
                ", status=" + status +
                '}';
    }
}
