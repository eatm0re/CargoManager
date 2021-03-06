package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.VehicleDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Order;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.*;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.VehicleService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class VehicleServiceImpl extends AbstractService<Vehicle, VehicleDTO> implements VehicleService {

    private static final double SPEED_KMH = 60.0;
    private static final long MONTH_WORK_TIME = (long) 176 * 3_600_000;

    public VehicleServiceImpl() {
        super(Vehicle.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public VehicleDTO findByRegNumber(String regNumber) throws BusinessException {
        if (!isSimpleName(regNumber)) {
            throw new WrongParameterException("Wrong registration number");
        }
        Vehicle entity = dao.getVehicleDAO().selectByRegNumber(regNumber);
        if (entity == null) {
            throw new EntityNotFoundException("Vehicle with registration number " + regNumber + " not found");
        }

        VehicleDTO dto = new VehicleDTO(entity);
        dto.fill(entity);
        return dto;
    }

    /**
     * Creates a new OK vehicle with specified registration number and capacity in name-specified city.
     *
     * @param vehicleDTO contains registration number, capacity (in KG) and city (only name needed) of creating vehicle.
     * @return ID of created vehicle.
     * @throws IllegalArgumentException in cases of wrong registration number, city name or negative capacity.
     * @throws EntityNotFoundException  if city with specified name does not exist.
     * @throws EntityExistsException    if vehicle with specified registration number is already exists.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public long add(VehicleDTO vehicleDTO) throws BusinessException {
        Vehicle vehicle = new Vehicle();

        // registration number
        String regNumber = vehicleDTO.getRegNumber();
        if (regNumber == null || regNumber.length() == 0) {
            throw new MissedParameterException("Registration number must be specified");
        }
        if (!isSimpleName(regNumber)) {
            throw new WrongParameterException("Wrong registration number");
        }
        if (dao.getVehicleDAO().selectByRegNumber(regNumber) != null) {
            throw new EntityExistsException("Vehicle with registration number " + regNumber + " is already exists");
        }
        vehicle.setRegNumber(regNumber);

        // capacity
        long capacityKg = vehicleDTO.getCapacityKg();
        if (capacityKg < 0) {
            throw new WrongParameterException("Vehicle capacity must be non-negative");
        }
        vehicle.setCapacityKg(capacityKg);

        // location
        if (vehicleDTO.getLocation() == null || vehicleDTO.getLocation().getName() == null || vehicleDTO.getLocation().getName().length() == 0) {
            throw new MissedParameterException("The city name must be specified");
        }
        String cityName = vehicleDTO.getLocation().getName();
        if (!isSimpleName(cityName)) {
            throw new WrongParameterException("Wrong city name");
        }
        City city = dao.getCityDAO().selectByName(cityName);
        if (city == null) {
            throw new EntityNotFoundException("City " + cityName + " not found");
        }
        vehicle.setLocation(city);

        // successfully read and checked attributes, creating vehicle
        dao.getVehicleDAO().insert(vehicle);
        return vehicle.getId();
    }

    /**
     * Changes properties of existing vehicle. Search for vehicle is performed by registration number, ID is ignored.
     * In case of the vehicle is not able to continue order executing after changes, these changes are not committed.
     * It is needed to interrupt order before apply these changes.
     *
     * @param vehicleDTO ID is ignored;
     *                   registration number is used for specifying vehicle;
     *                   in order the only field needed is ID (null also accepted);
     *                   in city only name required;
     *                   list of drivers is ignored.
     * @throws IllegalArgumentException in cases of wrong registration number, city name or negative capacity.
     * @throws EntityNotFoundException  if vehicle with specified registration number or
     *                                  city with specified name not found.
     * @throws IllegalStateException    in cases:
     *                                  1) of changing capacity during order that is vehicle can not go further;
     *                                  2) of breaking vehicle during order;
     *                                  3) of assigning non-suitable vehicle to order
     *                                  (busy, broken or with not enough capacity, in another city)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public void change(VehicleDTO vehicleDTO) throws BusinessException {

        // search by registration number
        String regNumber = vehicleDTO.getRegNumber();
        if (regNumber == null || regNumber.length() == 0) {
            throw new MissedParameterException("Registration number must be specified");
        }
        if (!isSimpleName(regNumber)) {
            throw new WrongParameterException("Wrong registration number");
        }
        Vehicle vehicle = dao.getVehicleDAO().selectByRegNumber(regNumber);
        if (vehicle == null) {
            throw new EntityNotFoundException("Vehicle with registration number " + regNumber + " not found");
        }

        // capacity
        long capacityKg = vehicleDTO.getCapacityKg();
        Order oldOrder = vehicle.getOrder();
        if (capacityKg != 0 && capacityKg != vehicle.getCapacityKg()) {
            if (capacityKg < 0) {
                throw new WrongParameterException("Vehicle capacity must be positive");
            }
            if (oldOrder != null && capacityKg < service.getOrderService().capacityNeeded(oldOrder.getId())) {
                // new capacity is less than needed to continue order execution
                throw new BusinessException("Order interruption needed");
            }
            vehicle.setCapacityKg(capacityKg);
        }

        // status
        Vehicle.Status status = vehicleDTO.getStatus();
        if (status != vehicle.getStatus() && status != null) {
            if (status == Vehicle.Status.BROKEN && oldOrder != null) {
                // breaking vehicle during order
                throw new BusinessException("Order interruption needed");
            }
            vehicle.setStatus(status);
        }

        // location
        if (vehicleDTO.getLocation() != null && vehicleDTO.getLocation().getName() != null && vehicleDTO.getLocation().getName().length() > 0) {
            String cityName = vehicleDTO.getLocation().getName();
            if (!isSimpleName(cityName)) {
                throw new WrongParameterException("Wrong city name");
            }
            if (!cityName.equals(vehicle.getLocation().getName())) {
                if (vehicle.getOrder() != null) {
                    throw new BusinessException("Attempted to move vehicle during order");
                }
                City city = dao.getCityDAO().selectByName(cityName);
                if (city == null) {
                    throw new EntityNotFoundException("City " + cityName + " not found");
                }
                dao.getVehicleDAO().move(vehicle, city);
                dao.getDriverDAO().moveAll(vehicle.getDrivers(), city);
            }
        }

        // order
        if (vehicleDTO.getOrder() == null || vehicleDTO.getOrder().getId() == 0) {
            if (oldOrder != null) {
                // order interruption
                service.getOrderService().interrupt(oldOrder.getId());
            }
        } else {
            long newOrderId = vehicleDTO.getOrder().getId();
            if (newOrderId < 0) {
                throw new WrongParameterException("Order ID must be positive");
            }
            if (oldOrder == null) {
                // assign vehicle to order
                if (status == Vehicle.Status.BROKEN) {
                    throw new BusinessException("Attempted to assign broken vehicle");
                }
                if (vehicle.getCapacityKg() < service.getOrderService().capacityNeeded(newOrderId)) {
                    throw new BusinessException("Not enough vehicle capacity for order");
                }
                Order newOrder = dao.getOrderDAO().selectById(newOrderId);
                if (newOrder == null) {
                    throw new EntityNotFoundException("Order #" + newOrderId + " not found");
                }
                int progress = newOrder.getProgress();
                City orderCity = newOrder.getCheckpoints().get(progress == 0 ? 0 : progress - 1).getCity();
                if (vehicle.getLocation().getId() != orderCity.getId()) {
                    throw new BusinessException("Vehicle must be in " + orderCity.getName());
                }
                if (timeProvided(vehicle.getDrivers()) < service.getOrderService().timeNeeded(newOrder.getId())) {
                    throw new BusinessException("Not enough drivers for order");
                }
                dao.getOrderDAO().assignVehicle(newOrder, vehicle);
            } else if (oldOrder.getId() != newOrderId) {
                throw new BusinessException("Attempted to assign busy vehicle");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public long timeProvided(String regNumber) throws BusinessException {
        if (!isSimpleName(regNumber)) {
            throw new WrongParameterException("Wrong registration number");
        }
        Vehicle vehicle = dao.getVehicleDAO().selectByRegNumber(regNumber);
        if (vehicle == null) {
            throw new EntityNotFoundException("Vehicle with registration number " + regNumber + " not found");
        }
        return timeProvided(vehicle.getDrivers());
    }

    private long timeProvided(Collection<Driver> drivers) {
        if (drivers == null) {
            return 0;
        }
        return drivers.stream().mapToLong(x -> MONTH_WORK_TIME - x.getWorkedThisMonthMs()).sum();
    }

    @Override
    public double getSpeedKMH() {
        return SPEED_KMH;
    }
}
