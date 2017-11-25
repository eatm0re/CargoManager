package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.OrderDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.VehicleDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.EntityNotFoundException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.MissedParameterException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.WrongParameterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
@WebAppConfiguration
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
                        new CityDTO(1, "Moscow", 55.755, 37.612, null, null),
                        null,
                        Collections.emptyList(),
                        Collections.emptyList()
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
        } catch (WrongParameterException e) {
            assertEquals("Wrong registration number", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    @SuppressWarnings("unchecked")
    public void add_1() throws Exception {
        VehicleDTO vehicle = new VehicleDTO("Test", 1000, new CityDTO("Saint-Petersburg"));
        long vehicleId = service.getVehicleService().add(vehicle);
        vehicle = service.getVehicleService().findByRegNumber("Test");
        assertEquals(
                new VehicleDTO(
                        vehicleId,
                        "Test",
                        1000,
                        Vehicle.Status.OK,
                        new CityDTO(2, "Saint-Petersburg", 59.952, 30.316, null, null), null, Collections.EMPTY_LIST,
                        Collections.emptyList()
                ),
                vehicle
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_2() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO("", 1000, new CityDTO("Saint-Petersburg"));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("Registration number must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_3() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO("Тест", 1000, new CityDTO("Saint-Petersburg"));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong registration number", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_4() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO("Test", -1000, new CityDTO("Saint-Petersburg"));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Vehicle capacity must be non-negative", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_5() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO("Test", 1000, null);
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("The city name must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_6() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO("Test", 1000, new CityDTO("Test"));
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
            VehicleDTO vehicle = new VehicleDTO("Test", 1000, new CityDTO("Тест"));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong city name", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_8() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO("Test", 1000, new CityDTO(""));
            service.getVehicleService().add(vehicle);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("The city name must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    @SuppressWarnings("unchecked")
    public void change_1() throws Exception {
        VehicleDTO vehicle = new VehicleDTO("5432DF", 14000, null);
        service.getVehicleService().change(vehicle);
        assertEquals(
                new VehicleDTO(
                        7,
                        "5432DF",
                        14000,
                        Vehicle.Status.OK,
                        new CityDTO(2, "Saint-Petersburg", 59.952, 30.316, null, null),
                        null,
                        Collections.emptyList(),
                        Collections.emptyList()
                ),
                service.getVehicleService().findByRegNumber("5432DF")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    @SuppressWarnings("unchecked")
    public void change_2() throws Exception {
        VehicleDTO vehicle = new VehicleDTO("5432DF", 0, new CityDTO("Moscow"));
        service.getVehicleService().change(vehicle);
        assertEquals(
                new VehicleDTO(
                        7,
                        "5432DF",
                        13000,
                        Vehicle.Status.OK,
                        new CityDTO(1, "Moscow", 55.755, 37.612, null, null),
                        null,
                        Collections.emptyList(),
                        Collections.emptyList()
                ),
                service.getVehicleService().findByRegNumber("5432DF")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    @SuppressWarnings("unchecked")
    public void change_3() throws Exception {
        VehicleDTO vehicle = new VehicleDTO("5432DF", 0, Vehicle.Status.BROKEN, null, null);
        service.getVehicleService().change(vehicle);
        assertEquals(
                new VehicleDTO(
                        7,
                        "5432DF",
                        13000,
                        Vehicle.Status.BROKEN,
                        new CityDTO(2, "Saint-Petersburg", 59.952, 30.316, null, null),
                        null,
                        Collections.emptyList(),
                        Collections.emptyList()
                ),
                service.getVehicleService().findByRegNumber("5432DF")
        );
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_4() throws Exception {
        DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
        driver.setVehicle(new VehicleDTO("5432DF"));
        service.getDriverService().change(driver);
        VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(2));
        service.getVehicleService().change(vehicle);
        assertEquals(2, service.getVehicleService().findByRegNumber("5432DF").getOrder().getId());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_5() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(2));
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Not enough drivers for order", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_6() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO("5432DF"));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO("5432DF", 1, null, null, new OrderDTO(2));
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Not enough vehicle capacity for order", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_7() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO("5432DF"));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO("5432DF", 0, Vehicle.Status.BROKEN, null, new OrderDTO(2));
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Attempted to assign broken vehicle", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_8() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO("5432DF"));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(-2));
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Order ID must be positive", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_9() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO("5432DF"));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(666_666_666_666L));
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
            VehicleDTO vehicle = new VehicleDTO("", 0, null, null, null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("Registration number must be specified", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_11() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO("Тест", 0, null, null, null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong registration number", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_12() throws Exception {
        try {
            VehicleDTO vehicle = new VehicleDTO("Test", 0, null, null, null);
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
            VehicleDTO vehicle = new VehicleDTO("5432DF", -1000, null, null, null);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Vehicle capacity must be positive", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_14() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO("5432DF"));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(2));
            service.getVehicleService().change(vehicle);
            vehicle.setCapacityKg(1);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Order interruption needed", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_15() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO("5432DF"));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(2));
            service.getVehicleService().change(vehicle);
            vehicle.setStatus(Vehicle.Status.BROKEN);
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Order interruption needed", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_16() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO("5432DF"));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(2));
            service.getVehicleService().change(vehicle);
            vehicle.setLocation(new CityDTO("Moscow"));
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Attempted to move vehicle during order", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_17() throws Exception {
        DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
        driver.setVehicle(new VehicleDTO("5432DF"));
        service.getDriverService().change(driver);
        VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(2));
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
            driver.setVehicle(new VehicleDTO("5432DF"));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(1));
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Vehicle must be in Kazan", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_19() throws Exception {
        try {
            DriverDTO driver = service.getDriverService().findByPersNumber("LZX321098");
            driver.setVehicle(new VehicleDTO("5432DF"));
            service.getDriverService().change(driver);
            VehicleDTO vehicle = new VehicleDTO("5432DF", 0, null, null, new OrderDTO(2));
            service.getVehicleService().change(vehicle);
            vehicle.setOrder(new OrderDTO(1));
            service.getVehicleService().change(vehicle);
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Attempted to assign busy vehicle", e.getMessage());
        }
    }
}
