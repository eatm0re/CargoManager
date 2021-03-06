package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CheckpointDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.OrderDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.TaskDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.*;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.EntityNotFoundException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.MissedParameterException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.WrongParameterException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.OrderService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl extends AbstractService<Order, OrderDTO> implements OrderService {

    public OrderServiceImpl() {
        super(Order.class);
    }

    /**
     * Creates order with specified checkpoints and cargoes.
     * During creating it checks for correct loading-unloading order and throws an exception if something went wrong.
     *
     * @param orderDTO list of checkpoints must be filled. Every checkpoint should contain its city name
     *                 (city will be found by name) and list of cargoes for loading or unloading.
     * @return ID of created vehicle.
     * @throws IllegalArgumentException if order details not fully filled, filled wrong
     *                                  or loading-unloading order is incorrect.
     * @throws EntityNotFoundException  if city with specified name or cargo with specified ID not found.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public long add(OrderDTO orderDTO) throws BusinessException {

        // creating order
        Order order = new Order();

        order.setCheckpoints(new LinkedList<>());

        // scan all checkpoints for order
        List<CheckpointDTO> checkpoints = orderDTO.getCheckpoints();
        if (checkpoints == null || checkpoints.size() < 2) {
            throw new BusinessException("Order must have at least 2 checkpoints");
        }
        HashSet<Long> loadedCargoes = new HashSet<>();
        for (CheckpointDTO checkpointDTO : checkpoints) {

            // creating checkpoint
            Checkpoint checkpoint = new Checkpoint();

            dao.getOrderDAO().addCheckpoint(order, checkpoint);

            // checkpoint city
            if (checkpointDTO.getCity() == null || checkpointDTO.getCity().getName() == null || checkpointDTO.getCity().getName().length() == 0) {
                throw new MissedParameterException("City name must be specified in every checkpoint");
            }
            String cityName = checkpointDTO.getCity().getName();
            if (!isSimpleName(cityName)) {
                throw new WrongParameterException("Wrong city name");
            }
            City city = dao.getCityDAO().selectByName(cityName);
            if (city == null) {
                throw new EntityNotFoundException("City " + cityName + " not found");
            }
            checkpoint.setCity(city);
            checkpoint.setTasks(new LinkedList<>());

            // scan all tasks for checkpoint
            List<TaskDTO> tasks = checkpointDTO.getTasks();
            if (tasks == null || tasks.isEmpty()) {
                throw new BusinessException("Each checkpoint must have at least one task");
            }
            for (TaskDTO taskDTO : tasks) {

                // creating task
                Task task = new Task();

                task.setCheckpoint(checkpoint);
                //
                checkpoint.getTasks().add(task);

                // bind the task with cargo
                long cargoId = taskDTO.getCargo().getId();
                if (cargoId <= 0) {
                    throw new WrongParameterException("Cargo ID must be positive");
                }
                Cargo cargo = dao.getCargoDAO().selectById(cargoId);
                if (cargo == null) {
                    throw new EntityNotFoundException("Cargo #" + cargoId + " not found");
                }
                if (loadedCargoes.contains(cargoId)) {
                    // task is unloading cargo
                    if (cargo.getLocation().getId() == checkpoint.getCity().getId()) {
                        throw new BusinessException("Unloading cargo in city it was loaded");
                    }
                    loadedCargoes.remove(cargoId);
                } else {
                    // task is loading cargo
                    if (cargo.getLocation().getId() != checkpoint.getCity().getId()) {
                        throw new BusinessException("Loading cargo when it is in another city");
                    }
                    if (cargo.getStatus() != Cargo.Status.READY) {
                        throw new BusinessException("Cargo is delivering by another order");
                    }
                    loadedCargoes.add(cargoId);
                }
                task.setCargo(cargo);
            }
        }
        if (!loadedCargoes.isEmpty()) {
            // some unloaded cargo remained at the end point
            throw new BusinessException("All loaded cargoes must be unloaded");
        }

        order.setTotal(checkpoints.size());
        dao.getOrderDAO().insert(order);
        return order.getId();
    }

    /**
     * Marks the current checkpoint as completed.
     * Order's vehicle, drivers and unloaded cargoes moves to the current city.
     * Loaded cargoes applies "SHIPPED" status, unloaded - "DELIVERED".
     *
     * @param id order ID to progress
     * @throws IllegalArgumentException if non-positive ID given.
     * @throws EntityNotFoundException  if specified non-existent order.
     * @throws IllegalStateException    if specified order is already completed, or no vehicle assigned to order.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public void progressReport(long id) throws BusinessException {

        // loading order
        if (id <= 0) {
            throw new WrongParameterException("Order ID must be positive");
        }
        Order order = dao.getOrderDAO().selectById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order #" + id + " not found");
        }

        if (order.getProgress() == order.getTotal()) {
            throw new BusinessException("Order #" + id + " is already completed");
        }
        Checkpoint checkpoint = order.getCheckpoints().get(order.getProgress());
        City city = checkpoint.getCity();
        order.setProgress(order.getProgress() + 1);

        // move vehicle to the new city
        Vehicle vehicle = order.getVehicle();
        if (vehicle == null) {
            throw new BusinessException("No vehicle assigned to order");
        }
        dao.getVehicleDAO().move(vehicle, city);
        if (order.getProgress() == order.getTotal()) {
            // order completed
            dao.getOrderDAO().unassignVehicle(order);
        }

        // move all drivers to the new city
        List<Driver> drivers = vehicle.getDrivers();
        for (Driver driver : drivers) {
            dao.getDriverDAO().move(driver, city);
            dao.getDriverDAO().updateStatus(driver, Driver.Status.REST);
        }

        // move all cargoes to the new city
        List<Cargo> cargoes = vehicle.getCargoes();
        for (Cargo cargo : cargoes) {
            dao.getCargoDAO().move(cargo, city);
        }

        // loading and unloading target cargoes
        List<Task> tasks = checkpoint.getTasks();
        for (Task task : tasks) {
            Cargo cargo = task.getCargo();
            if (cargo.getStatus() == Cargo.Status.READY) {
                // loading
                cargo.setStatus(Cargo.Status.SHIPPED);
                dao.getCargoDAO().load(cargo, vehicle);
            } else {
                // unloading
                cargo.setStatus(Cargo.Status.DELIVERED);
                dao.getCargoDAO().unload(cargo);
            }
        }
    }

    /**
     * Suspends order execution by setting vehicle and drivers free
     * and unloading all currently loaded cargoes in the current city.
     *
     * @param id order ID to interrupt.
     * @throws IllegalArgumentException if non-positive ID given.
     * @throws EntityNotFoundException  if order with specified ID not found.
     * @throws IllegalStateException    if specified order is already completed or suspended.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public void interrupt(long id) throws BusinessException {

        // loading order
        if (id <= 0) {
            throw new WrongParameterException("Order ID must be positive");
        }
        Order order = dao.getOrderDAO().selectById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order #" + id + " not found");
        }
        int progress = order.getProgress();
        if (progress == order.getTotal()) {
            throw new BusinessException("Order #" + id + " is already completed");
        }
        if (progress == 1) {
            dao.getOrderDAO().resetProgress(order);
        }

        // set vehicle free
        Vehicle vehicle = order.getVehicle();
        if (vehicle == null) {
            throw new BusinessException("No vehicle assigned to order");
        }
        dao.getOrderDAO().unassignVehicle(order);

        // set all drivers free
        List<Driver> drivers = vehicle.getDrivers();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        for (Driver driver : drivers) {
            if (driver.getStatus() == Driver.Status.WORK) {
                // update worked hours counter
                driver.setStatus(Driver.Status.REST);
                int diffHours = (int) ((now.getTime() - driver.getLastStatusUpdate().getTime() - 1) / 3_600_000 + 1);
                driver.setWorkedThisMonthMs(driver.getWorkedThisMonthMs() + diffHours);
            }
        }

        // scan all loaded cargoes at the moment of order interruption
        List<Cargo> cargoes = vehicle.getCargoes();
        for (Cargo cargo : cargoes) {
            cargo.setStatus(Cargo.Status.READY);
            dao.getCargoDAO().unload(cargo);
        }
    }

    /**
     * Calculates minimal vehicle capacity to complete an order.
     *
     * @param id order ID.
     * @return calculated capacity in KG.
     * @throws IllegalArgumentException if non-positive ID given.
     * @throws EntityNotFoundException  if order with specified ID not found.
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public long capacityNeeded(long id) throws BusinessException {

        // loading order
        if (id <= 0) {
            throw new WrongParameterException("Order ID must be positive");
        }
        Order order = dao.getOrderDAO().selectById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order #" + id + " not found");
        }

        // scan all cargoes
        BigDecimal maxWeight = BigDecimal.ZERO;
        BigDecimal currWeight = BigDecimal.ZERO;
        List<Checkpoint> checkpoints = order.getCheckpoints();
        HashSet<Cargo> loadedCargoes = new HashSet<>();
        for (Checkpoint checkpoint : checkpoints) {

            // scan all tasks for checkpoint
            List<Task> tasks = checkpoint.getTasks();
            for (Task task : tasks) {
                Cargo cargo = task.getCargo();
                if (loadedCargoes.contains(cargo)) {
                    // task is unloading cargo
                    loadedCargoes.remove(cargo);
                    currWeight = currWeight.subtract(cargo.getWeightKg());
                } else {
                    // task is loading cargo
                    loadedCargoes.add(cargo);
                    currWeight = currWeight.add(cargo.getWeightKg());
                    if (currWeight.compareTo(maxWeight) > 0) {
                        maxWeight = currWeight;
                    }
                }
            }
        }

        try {
            return maxWeight.longValueExact();
        } catch (ArithmeticException e) {
            return maxWeight.longValue() + 1;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public long timeNeeded(long orderId) throws BusinessException {
        if (orderId <= 0) {
            throw new WrongParameterException("Order ID must be positive");
        }
        Order order = dao.getOrderDAO().selectById(orderId);
        if (order == null) {
            throw new EntityNotFoundException("Order #" + orderId + " not found");
        }
        return timeNeeded(order);
    }

    private long timeNeeded(Order order) throws BusinessException {
        double distance = 0.0;
        List<Checkpoint> checkpoints = order.getCheckpoints();
        String prevCityName = checkpoints.get(0).getCity().getName();
        for (Checkpoint checkpoint : checkpoints) {
            String currCityName = checkpoint.getCity().getName();
            distance += service.getCityService().distance(prevCityName, currCityName);
            prevCityName = currCityName;
        }
        return Math.round(distance / service.getVehicleService().getSpeedKMH() * 3_600_000);
    }
}
