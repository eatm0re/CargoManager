package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities", schema = "cargomanager")
public class City {
    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private List<Driver> drivers;
    private List<Vehicle> vehicles;

    @Id
    @Column(name = "idCity", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cityName", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "cityLatitude", nullable = false, precision = 3)
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "cityLongitude", nullable = false, precision = 3)
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (Double.compare(city.latitude, latitude) != 0) return false;
        if (Double.compare(city.longitude, longitude) != 0) return false;
        return name != null ? name.equals(city.name) : city.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @OneToMany(mappedBy = "location")
    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @OneToMany(mappedBy = "location")
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
