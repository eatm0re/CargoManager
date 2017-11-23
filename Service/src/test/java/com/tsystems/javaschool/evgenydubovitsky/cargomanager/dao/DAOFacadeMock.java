package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.AbstractEntity;

import static org.powermock.api.mockito.PowerMockito.mock;

public class DAOFacadeMock implements DAOFacade {

    private CargoDAO cargoDAO = mock(CargoDAO.class);
    private CheckpointDAO checkpointDAO = mock(CheckpointDAO.class);
    private CityDAO cityDAO = mock(CityDAO.class);
    private DriverDAO driverDAO = mock(DriverDAO.class);
    private OrderDAO orderDAO = mock(OrderDAO.class);
    private TaskDAO taskDAO = mock(TaskDAO.class);
    private UserDAO userDAO = mock(UserDAO.class);
    private VehicleDAO vehicleDAO = mock(VehicleDAO.class);

    @Override
    public <E extends AbstractEntity> DAO<E> getDAO(Class<E> entityClass) {
        return null;
    }

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
