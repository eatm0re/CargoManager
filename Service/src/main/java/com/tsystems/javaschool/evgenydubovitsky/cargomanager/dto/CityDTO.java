package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

public class CityDTO extends DTO<City> {

    private String name;
    private double latitude;
    private double longitude;
    private List<DriverDTO> drivers;
    private List<VehicleDTO> vehicles;

    public CityDTO(long id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CityDTO(long id, String name, double latitude, double longitude, List<DriverDTO> drivers, List<VehicleDTO> vehicles) {
        this(id, name, latitude, longitude);
        this.drivers = drivers;
        this.vehicles = vehicles;
    }

    public CityDTO(City entity) {
        this(entity.getId(), entity.getName(), entity.getLatitude(), entity.getLongitude());
    }

    @Override
    public void fill(City entity) {
        List<Driver> drivers = entity.getDrivers();
        this.drivers = drivers.stream().map(DriverDTO::new).collect(Collectors.toList());

        List<Vehicle> vehicles = entity.getVehicles();
        this.vehicles = vehicles.stream().map(VehicleDTO::new).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<DriverDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDTO> drivers) {
        this.drivers = drivers;
    }

    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public String toString() {
        return "CityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityDTO)) return false;
        if (!super.equals(o)) return false;

        CityDTO cityDTO = (CityDTO) o;

        if (Double.compare(cityDTO.latitude, latitude) != 0) return false;
        if (Double.compare(cityDTO.longitude, longitude) != 0) return false;
        if (name != null ? !name.equals(cityDTO.name) : cityDTO.name != null) return false;
        if (drivers != null ? !drivers.equals(cityDTO.drivers) : cityDTO.drivers != null) return false;
        return vehicles != null ? vehicles.equals(cityDTO.vehicles) : cityDTO.vehicles == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (drivers != null ? drivers.hashCode() : 0);
        result = 31 * result + (vehicles != null ? vehicles.hashCode() : 0);
        return result;
    }
}
