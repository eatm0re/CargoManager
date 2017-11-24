package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.DriverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@Service
public class DriverServiceImpl extends AbstractService<Driver, DriverDTO> implements DriverService {

    private static final long MONTH_WORK_TIME = 176 * 3_600_000;

    public DriverServiceImpl() {
        super(Driver.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    public DriverDTO findByPersNumber(String persNumber) {
        if (!isSimpleName(persNumber)) {
            throw new IllegalArgumentException("Wrong registration number");
        }
        Driver entity = dao.getDriverDAO().selectByPersNumber(persNumber);
        if (entity == null) {
            throw new EntityNotFoundException("Driver with personal number " + persNumber + " not found");
        }
        DriverDTO dto = new DriverDTO(entity);
        dto.fill(entity);
        return dto;
    }

    /**
     * Creates a new driver with specified personal number, first name, last name and in specified city.
     *
     * @param driverDTO must contain personal number, first name, last name and city name.
     * @return ID of created driver.
     * @throws IllegalArgumentException if given personal number, first name, last name or city name are wrong.
     * @throws EntityExistsException    if driver with specified personal number is already exists.
     * @throws EntityNotFoundException  if city with specified name does not exist.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long add(DriverDTO driverDTO) {

        // creating driver
        Driver driver = new Driver();

        // personal number
        String persNumber = driverDTO.getPersNumber();
        if (persNumber == null || persNumber.length() == 0) {
            throw new IllegalArgumentException("Personal number must be specified");
        }
        if (!isSimpleName(persNumber)) {
            throw new IllegalArgumentException("Wrong personal number");
        }
        if (dao.getDriverDAO().selectByPersNumber(persNumber) != null) {
            throw new EntityExistsException("Driver with personal number " + persNumber + " is already exists");
        }
        driver.setPersNumber(persNumber);

        // first name
        String firstName = driverDTO.getFirstName();
        if (firstName == null || firstName.length() == 0) {
            throw new IllegalArgumentException("First name must be specified");
        }
        if (!isSimpleName(firstName)) {
            throw new IllegalArgumentException("Wrong first name");
        }
        driver.setFirstName(firstName);

        // last name
        String lastName = driverDTO.getLastName();
        if (lastName == null || lastName.length() == 0) {
            throw new IllegalArgumentException("Last name must be specified");
        }
        if (!isSimpleName(lastName)) {
            throw new IllegalArgumentException("Wrong last name");
        }
        driver.setLastName(lastName);

        // location
        if (driverDTO.getLocation() == null || driverDTO.getLocation().getName() == null || driverDTO.getLocation().getName().length() == 0) {
            throw new IllegalArgumentException("City name must be specified");
        }
        String cityName = driverDTO.getLocation().getName();
        if (!isSimpleName(cityName)) {
            throw new IllegalArgumentException("Wrong city name");
        }
        City city = dao.getCityDAO().selectByName(cityName);
        if (city == null) {
            throw new EntityNotFoundException("City " + cityName + " not found");
        }
        driver.setLocation(city);

        // insert
        dao.getDriverDAO().insert(driver);
        return driver.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void change(DriverDTO driverDTO) {

        // find by personal number
        String persNumber = driverDTO.getPersNumber();
        if (persNumber == null || persNumber.length() == 0) {
            throw new IllegalArgumentException("Personal number must be specified");
        }
        if (!isSimpleName(persNumber)) {
            throw new IllegalArgumentException("Wrong personal number");
        }
        Driver driver = dao.getDriverDAO().selectByPersNumber(persNumber);
        if (driver == null) {
            throw new EntityNotFoundException("Driver with personal number " + persNumber + " not found");
        }

        // first name
        String firstName = driverDTO.getFirstName();
        if (firstName != null && firstName.length() > 0 && !firstName.equals(driver.getFirstName())) {
            if (!isSimpleName(firstName)) {
                throw new IllegalArgumentException("Wrong first name");
            }
            driver.setFirstName(firstName);
        }

        // last name
        String lastName = driverDTO.getLastName();
        if (lastName != null && lastName.length() > 0 && !lastName.equals(driver.getLastName())) {
            if (!isSimpleName(lastName)) {
                throw new IllegalArgumentException("Wrong last name");
            }
            driver.setLastName(lastName);
        }

        // location
        if (driverDTO.getLocation() != null
                && driverDTO.getLocation().getName() != null
                && driverDTO.getLocation().getName().length() > 0
                && !driverDTO.getLocation().getName().equals(driver.getLocation().getName())) {

            if (driver.getVehicle() != null) {
                throw new IllegalStateException("Attempted to move assigned driver");
            }

            String cityName = driverDTO.getLocation().getName();
            if (!isSimpleName(cityName)) {
                throw new IllegalArgumentException("Wrong city name");
            }
            City city = dao.getCityDAO().selectByName(cityName);
            if (city == null) {
                throw new EntityNotFoundException("City " + cityName + " not found");
            }
            dao.getDriverDAO().move(driver, city);
        }

        // vehicle
        if (driverDTO.getVehicle() == null || driverDTO.getVehicle().getRegNumber() == null || driverDTO.getVehicle().getRegNumber().length() == 0) {
            if (driver.getVehicle() != null) {
                // unbind driver from vehicle
                if (driver.getStatus() == Driver.Status.WORK) {
                    throw new IllegalStateException("Attempted to unbind busy driver from vehicle");
                }
                if (driver.getVehicle().getDrivers().size() == 1) {
                    service.getOrderService().interrupt(driver.getVehicle().getOrder().getId());
                }
                dao.getDriverDAO().unbind(driver);
            }
        } else {
            String regNumber = driverDTO.getVehicle().getRegNumber();
            if (driver.getVehicle() == null || !regNumber.equals(driver.getVehicle().getRegNumber())) {
                // bind driver to vehicle
                if (driver.getVehicle() != null) {
                    // bind driver to another vehicle
                    if (driver.getVehicle().getOrder() != null) {
                        throw new IllegalStateException("Attempted to assign already assigned driver");
                    }
                }
                if (!isSimpleName(regNumber)) {
                    throw new IllegalArgumentException("Wrong registration number");
                }
                Vehicle vehicle = dao.getVehicleDAO().selectByRegNumber(regNumber);
                if (vehicle == null) {
                    throw new EntityNotFoundException("Vehicle with registration number " + regNumber + " not found");
                }
                if (driver.getLocation().getId() != vehicle.getLocation().getId()) {
                    throw new IllegalStateException("Attempted to assign driver to vehicle in another city");
                }
                dao.getDriverDAO().bind(driver, vehicle);
            }
        }

        // status
        Driver.Status status = driverDTO.getStatus();
        if (status != null) {
            if (status == Driver.Status.WORK) {
                Vehicle vehicle = driver.getVehicle();
                if (vehicle == null || vehicle.getOrder() == null) {
                    throw new IllegalStateException("To WORK status driver must be assigned to order");
                }
                if (driver.getWorkedThisMonthMs() >= MONTH_WORK_TIME) {
                    throw new IllegalStateException("Driver monthly work time exceeded");
                }
                if (isAnybodyWorking(vehicle.getDrivers())) {
                    throw new IllegalStateException("Someone is already on wheel");
                }
            }
            dao.getDriverDAO().updateStatus(driver, status);
        }
    }

    private boolean isAnybodyWorking(Collection<Driver> drivers) {
        for (Driver driver : drivers) {
            if (driver.getStatus() == Driver.Status.WORK) {
                return true;
            }
        }
        return false;
    }
}
