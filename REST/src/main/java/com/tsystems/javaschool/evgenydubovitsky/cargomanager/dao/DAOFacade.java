package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.AbstractEntity;

public interface DAOFacade {

    CargoDAO getCargoDAO();

    CheckpointDAO getCheckpointDAO();

    CityDAO getCityDAO();

    DriverDAO getDriverDAO();

    OrderDAO getOrderDAO();

    TaskDAO getTaskDAO();

    UserDAO getUserDAO();

    VehicleDAO getVehicleDAO();

    @SuppressWarnings("unchecked")
    default <E extends AbstractEntity> DAO<E> getDAO(Class<E> entityClass) {
        if (entityClass.equals(getCargoDAO().getEntityClass())) {
            return (DAO<E>) getCargoDAO();
        }
        if (entityClass.equals(getCheckpointDAO().getEntityClass())) {
            return (DAO<E>) getCheckpointDAO();
        }
        if (entityClass.equals(getCityDAO().getEntityClass())) {
            return (DAO<E>) getCityDAO();
        }
        if (entityClass.equals(getDriverDAO().getEntityClass())) {
            return (DAO<E>) getDriverDAO();
        }
        if (entityClass.equals(getOrderDAO().getEntityClass())) {
            return (DAO<E>) getOrderDAO();
        }
        if (entityClass.equals(getTaskDAO().getEntityClass())) {
            return (DAO<E>) getTaskDAO();
        }
        if (entityClass.equals(getUserDAO().getEntityClass())) {
            return (DAO<E>) getUserDAO();
        }
        if (entityClass.equals(getVehicleDAO().getEntityClass())) {
            return (DAO<E>) getVehicleDAO();
        }
        throw new IllegalArgumentException("Entity class " + entityClass.getSimpleName() + " not found");
    }
}
