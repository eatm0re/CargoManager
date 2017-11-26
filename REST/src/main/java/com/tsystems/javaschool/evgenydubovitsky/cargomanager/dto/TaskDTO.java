package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Task;

public class TaskDTO extends DTO<Task> {

    private CheckpointDTO checkpoint;
    private CargoDTO cargo;

    /**
     * Empty
     */
    public TaskDTO() {
    }

    public TaskDTO(long id) {
        this.id = id;
    }

    public TaskDTO(long id, CheckpointDTO checkpoint, CargoDTO cargo) {
        this(id);
        this.checkpoint = checkpoint;
        this.cargo = cargo;
    }

    public TaskDTO(Task entity) {
        this(entity.getId());
    }

    @Override
    public void fill(Task entity) {
        checkpoint = new CheckpointDTO(entity.getCheckpoint());
        cargo = new CargoDTO(entity.getCargo());
    }

    public CheckpointDTO getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(CheckpointDTO checkpoint) {
        this.checkpoint = checkpoint;
    }

    public CargoDTO getCargo() {
        return cargo;
    }

    public void setCargo(CargoDTO cargo) {
        this.cargo = cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDTO)) return false;
        if (!super.equals(o)) return false;

        TaskDTO taskDTO = (TaskDTO) o;

        if (checkpoint != null ? !checkpoint.equals(taskDTO.checkpoint) : taskDTO.checkpoint != null) return false;
        return cargo != null ? cargo.equals(taskDTO.cargo) : taskDTO.cargo == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (checkpoint != null ? checkpoint.hashCode() : 0);
        result = 31 * result + (cargo != null ? cargo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                '}';
    }
}
