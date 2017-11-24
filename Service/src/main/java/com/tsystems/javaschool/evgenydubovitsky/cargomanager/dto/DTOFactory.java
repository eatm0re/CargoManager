package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.*;

public class DTOFactory {

    private DTOFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    @SuppressWarnings("unchecked")
    public static <E extends AbstractEntity> DTO<E> createDTO(E entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof Cargo) {
            return (DTO<E>) new CargoDTO((Cargo) entity);
        }
        if (entity instanceof Checkpoint) {
            return (DTO<E>) new CheckpointDTO((Checkpoint) entity);
        }
        if (entity instanceof City) {
            return (DTO<E>) new CityDTO((City) entity);
        }
        if (entity instanceof Driver) {
            return (DTO<E>) new DriverDTO((Driver) entity);
        }
        if (entity instanceof Order) {
            return (DTO<E>) new OrderDTO((Order) entity);
        }
        if (entity instanceof Task) {
            return (DTO<E>) new TaskDTO((Task) entity);
        }
        if (entity instanceof User) {
            return (DTO<E>) new UserDTO((User) entity);
        }
        if (entity instanceof Vehicle) {
            return (DTO<E>) new VehicleDTO((Vehicle) entity);
        }
        throw new IllegalArgumentException("Entity " + entity.getClass().getSimpleName() + " not found");
    }

    @SuppressWarnings("unchecked")
    public static <E extends AbstractEntity> Class<E> getEntityClass(DTO<E> dto) {
        if (dto instanceof CargoDTO) {
            return (Class<E>) Cargo.class;
        }
        if (dto instanceof CheckpointDTO) {
            return (Class<E>) Checkpoint.class;
        }
        if (dto instanceof CityDTO) {
            return (Class<E>) City.class;
        }
        if (dto instanceof DriverDTO) {
            return (Class<E>) Driver.class;
        }
        if (dto instanceof OrderDTO) {
            return (Class<E>) Order.class;
        }
        if (dto instanceof TaskDTO) {
            return (Class<E>) Task.class;
        }
        if (dto instanceof UserDTO) {
            return (Class<E>) User.class;
        }
        if (dto instanceof VehicleDTO) {
            return (Class<E>) Vehicle.class;
        }
        throw new IllegalArgumentException("Entity for " + dto.getClass().getSimpleName() + " not found");
    }
}
