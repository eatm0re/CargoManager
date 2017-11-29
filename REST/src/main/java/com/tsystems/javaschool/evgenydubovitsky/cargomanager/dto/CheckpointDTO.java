package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Checkpoint;

import java.util.List;
import java.util.stream.Collectors;

public class CheckpointDTO extends DTO<Checkpoint> {

    private OrderDTO order;
    private CityDTO city;
    private List<TaskDTO> tasks;

    /**
     * Empty
     */
    public CheckpointDTO() {
    }

    public CheckpointDTO(long id) {
        this.id = id;
    }

    public CheckpointDTO(long id, OrderDTO order, CityDTO city, List<TaskDTO> tasks) {
        this(id);
        this.order = order;
        this.city = city;
        this.tasks = tasks;
    }

    public CheckpointDTO(Checkpoint entity) {
        this(entity.getId());
    }

    @Override
    public void fill(Checkpoint entity) {
        city = new CityDTO(entity.getCity());
        this.tasks = entity.getTasks().stream().map(x -> {
            TaskDTO res = new TaskDTO(x);
            res.fill(x);
            return res;
        }).collect(Collectors.toList());
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckpointDTO)) return false;
        if (!super.equals(o)) return false;

        CheckpointDTO that = (CheckpointDTO) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        return tasks != null ? tasks.equals(that.tasks) : that.tasks == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CheckpointDTO{" +
                "id=" + id +
                ", order=" + order +
                ", city=" + city +
                ", tasks=" + tasks +
                '}';
    }
}
