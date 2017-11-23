package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "checkpoints", schema = "cargomanager")
public class Checkpoint extends AbstractEntity {

    private Order order;
    private City city;
    private List<Task> tasks;

    @Id
    @Column(name = "idCheckpoint", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "checkpointOrderId", referencedColumnName = "idOrder", nullable = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne
    @JoinColumn(name = "checkpointCityId", referencedColumnName = "idCity", nullable = false)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @OneToMany(mappedBy = "checkpoint", cascade = {CascadeType.PERSIST})
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Checkpoint that = (Checkpoint) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "id=" + id +
                '}';
    }
}
