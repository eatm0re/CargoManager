package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Checkpoint;

import java.io.Serializable;

public class TaskDTO implements Serializable {

    private long id;
    private Checkpoint checkpoint;
    private Cargo cargo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                '}';
    }
}
