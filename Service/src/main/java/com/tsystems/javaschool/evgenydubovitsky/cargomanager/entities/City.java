package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.LazyListProxy;
import org.hibernate.collection.spi.PersistentCollection;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "cities", schema = "cargomanager")
public class City extends AbstractEntity {

    private String name;
    private double latitude;
    private double longitude;
    private List<Driver> drivers;
    private List<Vehicle> vehicles;

    private boolean driversProxied;
    private boolean vehiclesProxied;

    public City() {
    }

    public City(long id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Id
    @Column(name = "idCity", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "location")
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

    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    public boolean removeDriver(Driver driver) {
        if (!driversProxied && drivers instanceof PersistentCollection) {
            drivers = new LazyListProxy<>(drivers);
            driversProxied = true;
        }
        return drivers.remove(driver);
    }

    @OneToMany(mappedBy = "location")
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void putVehicle(Vehicle vehicle) {
        if (vehicles == null) {
            vehicles = new LinkedList<>();
        }
        vehicles.add(vehicle);
    }

    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    public boolean removeVehicle(Vehicle vehicle) {
        if (!vehiclesProxied && vehicles instanceof PersistentCollection) {
            vehicles = new LazyListProxy<>(vehicles);
            vehiclesProxied = true;
        }
        return vehicles.remove(vehicle);
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

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
