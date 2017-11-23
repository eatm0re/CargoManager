package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Checkpoint;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Order;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO extends DTO<Order> {

    private int progress;
    private int total;
    private List<CheckpointDTO> checkpoints;
    private VehicleDTO vehicle;

    /**
     * Identifier
     */
    public OrderDTO(long id) {
        this.id = id;
    }

    /**
     * Creator
     */
    public OrderDTO(List<CheckpointDTO> checkpoints) {
        this.checkpoints = checkpoints;
    }

    /**
     * Full
     */
    public OrderDTO(long id, int progress, int total, List<CheckpointDTO> checkpoints, VehicleDTO vehicle) {
        this.id = id;
        this.progress = progress;
        this.total = total;
        this.checkpoints = checkpoints;
        this.vehicle = vehicle;
    }

    /**
     * Converter
     */
    public OrderDTO(Order entity) {
        this(
                entity.getId(),
                entity.getProgress(),
                entity.getTotal(),
                null,
                null
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fill(Order entity) {
        List<Checkpoint> checkpoints = entity.getCheckpoints();
        if (checkpoints == null) {
            this.checkpoints = Collections.EMPTY_LIST;
        } else {
            this.checkpoints = checkpoints.stream().map(CheckpointDTO::new).collect(Collectors.toList());
        }

        vehicle = entity.getVehicle() == null ? null : new VehicleDTO(entity.getVehicle());
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CheckpointDTO> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<CheckpointDTO> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;
        if (!super.equals(o)) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (progress != orderDTO.progress) return false;
        if (total != orderDTO.total) return false;
        if (checkpoints != null ? !checkpoints.equals(orderDTO.checkpoints) : orderDTO.checkpoints != null)
            return false;
        return vehicle != null ? vehicle.equals(orderDTO.vehicle) : orderDTO.vehicle == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + progress;
        result = 31 * result + total;
        result = 31 * result + (checkpoints != null ? checkpoints.hashCode() : 0);
        result = 31 * result + (vehicle != null ? vehicle.hashCode() : 0);
        return result;
    }
}
