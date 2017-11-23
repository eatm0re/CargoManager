package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.OrderDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.VehicleDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class VehicleServiceTest {

    private ServiceFacade service;

    @Autowired
    public void setService(ServiceFacade service) {
        this.service = service;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findByRegNumber_1() throws Exception {
        VehicleDTO vehicle = service.getVehicleService().findByRegNumber("5678ER");
        assertEquals(
                new VehicleDTO(
                        2,
                        "5678ER",
                        17000,
                        Vehicle.Status.OK,
                        Collections.EMPTY_LIST,
                        null,
                        new CityDTO(1, "Moscow", 55.755, 37.612)
                ),
                vehicle
        );
    }

    @Test
    public void findByRegNumber_2() throws Exception {
        try {
            service.getVehicleService().findByRegNumber("lol");
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("Vehicle with registration number lol not found", e.getMessage());
        }
    }

    @Test
    public void findByRegNumber_3() throws Exception {
        try {
            service.getVehicleService().findByRegNumber("лол");
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong registration number", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    @SuppressWarnings("unchecked")
    public void add_1() throws Exception {
        VehicleDTO vehicle = new VehicleDTO(0, "Test", 1000, null, null, null, new CityDTO(0, "Saint-Petersburg", 0, 0));
        long vehicleId = service.getVehicleService().add(vehicle);
        vehicle = service.getVehicleService().findByRegNumber("Test");
        assertEquals(
                new VehicleDTO(
                        vehicleId,
                        "Test",
                        1000,
                        Vehicle.Status.OK,
                        Collections.EMPTY_LIST,
                        null,
                        new CityDTO(2, "Saint-Petersburg", 59.952, 30.316)
                ),
                vehicle
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_2() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, null, 1000, null, null, null, new CityDTO(0, "Saint-Petersburg", 0, 0));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Registration number must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_3() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "Тест", 1000, null, null, null, new CityDTO(0, "Saint-Petersburg", 0, 0));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong registration number", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_4() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "Test", -1000, null, null, null, new CityDTO(0, "Saint-Petersburg", 0, 0));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Vehicle capacity must be non-negative", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_5() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "Test", 1000, null, null, null, null);
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("The city name must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_6() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "Test", 1000, null, null, null, new CityDTO(0, "Test", 0, 0));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("City Test not found", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_7() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "Test", 1000, null, null, null, new CityDTO(0, "Тест", 0, 0));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong city name", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_8() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "Test", 1000, null, null, null, new CityDTO(0, null, 0, 0));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("The city name must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    @SuppressWarnings("unchecked")
    public void change_1() throws Exception {
        VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 14000, null, null, null, null);
        service.getVehicleService().change(vehicle);
        assertEquals(
                new VehicleDTO(
                        7,
                        "5432DF",
                        14000,
                        Vehicle.Status.OK,
                        Collections.EMPTY_LIST,
                        null,
                        new CityDTO(2, "Saint-Petersburg", 59.952, 30.316)
                ),
                service.getVehicleService().findByRegNumber("5432DF")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    @SuppressWarnings("unchecked")
    public void change_2() throws Exception {
        VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, null, new CityDTO(0, "Moscow", 0, 0));
        service.getVehicleService().change(vehicle);
        vehicle = service.getVehicleService().findByRegNumber("5432DF");
        assertEquals(
                new VehicleDTO(
                        7,
                        "5432DF",
                        13000,
                        Vehicle.Status.OK,
                        Collections.EMPTY_LIST,
                        null,
                        new CityDTO(1, "Moscow", 55.755, 37.612)
                ),
                service.getVehicleService().findByRegNumber("5432DF")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    @SuppressWarnings("unchecked")
    public void change_3() throws Exception {
        VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, Vehicle.Status.BROKEN, null, null, null);
        service.getVehicleService().change(vehicle);
        assertEquals(
                new VehicleDTO(
                        7,
                        "5432DF",
                        13000,
                        Vehicle.Status.BROKEN,
                        Collections.EMPTY_LIST,
                        null,
                        new CityDTO(2, "Saint-Petersburg", 59.952, 30.316)
                ),
                service.getVehicleService().findByRegNumber("5432DF")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_4() throws Exception {
        DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
        driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
        service.getDriverService().change(driver);
        VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(2, 0, 0), null);
        service.getVehicleService().change(vehicle);
        assertEquals(2, service.getVehicleService().findByRegNumber("5432DF").getOrder().getId());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_5() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(2, 0, 0), null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Not enough drivers for order", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_6() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 1, null, null, new OrderDTO(2, 0, 0), null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Not enough vehicle capacity for order", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_7() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, Vehicle.Status.BROKEN, null, new OrderDTO(2, 0, 0), null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Attempted to assign broken vehicle", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_8() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(-2, 0, 0), null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Order ID must be positive", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_9() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(666_666_666_666L, 0, 0), null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("Order #666666666666 not found", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_10() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, null, 0, null, null, null, null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Registration number must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_11() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "Тест", 0, null, null, null, null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong registration number", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_12() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "Test", 0, null, null, null, null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("Vehicle with registration number Test not found", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_13() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", -1000, null, null, null, null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Vehicle capacity must be positive", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_14() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(2, 0, 0), null);
            service.getVehicleService().change(vehicle);
            vehicle.setCapacityKg(1);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Order interruption needed", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_15() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(2, 0, 0), null);
            service.getVehicleService().change(vehicle);
            vehicle.setStatus(Vehicle.Status.BROKEN);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Order interruption needed", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_16() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(2, 0, 0), null);
            service.getVehicleService().change(vehicle);
            vehicle.setLocation(new CityDTO(0, "Moscow", 0, 0));
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Attempted to move vehicle during order", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_17() throws Exception {
        DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
        driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
        service.getDriverService().change(driver);
        VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(2, 0, 0), null);
        service.getVehicleService().change(vehicle);
        vehicle.setOrder(null);
        service.getVehicleService().change(vehicle);
        assertNull(service.getVehicleService().findByRegNumber("5432DF").getOrder());
        assertNull(service.getOrderService().findById(2).getVehicle());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_18() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(1, 0, 0), null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Vehicle must be in Kazan", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_19() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO(0, "5432DF", 0, null));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO(0, "5432DF", 0, null, null, new OrderDTO(2, 0, 0), null);
            service.getVehicleService().change(vehicle);
            vehicle.setOrder(new OrderDTO(1, 0, 0));
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Attempted to assign busy vehicle", e.getMessage());
        }
    }
}
