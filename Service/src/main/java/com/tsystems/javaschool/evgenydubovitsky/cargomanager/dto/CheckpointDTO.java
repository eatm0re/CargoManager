package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Order;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Task;

import java.util.List;

public class CheckpointDTO extends DTO {

    private Order order;
    private City city;
    private List<Task> tasks;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "CheckpointDTO{" +
                "id=" + id +
                '}';
    }
}
