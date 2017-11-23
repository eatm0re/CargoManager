package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DAOFacadeImpl implements DAOFacade {

    private CargoDAO cargoDAO;
    private CheckpointDAO checkpointDAO;
    private CityDAO cityDAO;
    private DriverDAO driverDAO;
    private OrderDAO orderDAO;
    private TaskDAO taskDAO;
    private UserDAO userDAO;
    private VehicleDAO vehicleDAO;

    @Autowired
    public void setCargoDAO(CargoDAO cargoDAO) {
        this.cargoDAO = cargoDAO;
    }

    @Autowired
    public void setCheckpointDAO(CheckpointDAO checkpointDAO) {
        this.checkpointDAO = checkpointDAO;
    }

    @Autowired
    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Autowired
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Autowired
    public void setTaskDAO(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

//    @Override
//    @SuppressWarnings("unchecked")
//    public <E extends AbstractEntity> DAO<E> getDAO(Class<E> entityClass) {
//        if (entityClass.equals(cargoDAO.getEntityClass())) {
//            return (DAO<E>) cargoDAO;
//        }
//        if (entityClass.equals(checkpointDAO.getEntityClass())) {
//            return (DAO<E>) checkpointDAO;
//        }
//        if (entityClass.equals(cityDAO.getEntityClass())) {
//            return (DAO<E>) cityDAO;
//        }
//        if (entityClass.equals(driverDAO.getEntityClass())) {
//            return (DAO<E>) driverDAO;
//        }
//        if (entityClass.equals(orderDAO.getEntityClass())) {
//            return (DAO<E>) orderDAO;
//        }
//        if (entityClass.equals(taskDAO.getEntityClass())) {
//            return (DAO<E>) taskDAO;
//        }
//        if (entityClass.equals(userDAO.getEntityClass())) {
//            return (DAO<E>) userDAO;
//        }
//        if (entityClass.equals(vehicleDAO.getEntityClass())) {
//            return (DAO<E>) vehicleDAO;
//        }
//        throw new IllegalArgumentException("Entity " + entityClass.getSimpleName() + " not found");
//    }

    @Override
    public CargoDAO getCargoDAO() {
        return cargoDAO;
    }

    @Override
    public CheckpointDAO getCheckpointDAO() {
        return checkpointDAO;
    }

    @Override
    public CityDAO getCityDAO() {
        return cityDAO;
    }

    @Override
    public DriverDAO getDriverDAO() {
        return driverDAO;
    }

    @Override
    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    @Override
    public TaskDAO getTaskDAO() {
        return taskDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }
}
