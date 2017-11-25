package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "orders", schema = "cargomanager")
public class Order extends AbstractEntity {

    private int progress;
    private int total;
    private List<Checkpoint> checkpoints;
    private Vehicle vehicle;

    @Id
    @Column(name = "idOrder", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "orderProgress", nullable = false)
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Basic
    @Column(name = "orderTotal", nullable = false)
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST})
    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public void putCheckpoint(Checkpoint checkpoint) {
        if (checkpoints == null) {
            checkpoints = new LinkedList<>();
        }
        checkpoints.add(checkpoint);
    }

    @OneToOne
    @JoinColumn(name = "orderVehicleId", referencedColumnName = "idVehicle")
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (progress != order.progress) return false;
        return total == order.total;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + progress;
        result = 31 * result + total;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", progress=" + progress +
                ", total=" + total +
                '}';
    }
}
