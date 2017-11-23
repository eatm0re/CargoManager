package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacade;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacadeMock;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl.DriverServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class DriverServiceTest {

    private DAOFacade dao;
    private DriverServiceImpl service;

    @Before
    public void setUp() throws Exception {
        dao = new DAOFacadeMock();
        service = new DriverServiceImpl();
        service.setDao(dao);
    }

    @Test
    public void add_1() throws Exception {
        when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
        DriverDTO driver = new DriverDTO(0, "Test1", "Test2", "Test3", null, null, 0);
        driver.setLocation(new CityDTO(0, "TestCity", 0, 0));
        service.add(driver);
    }

    @Test
    public void add_2() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO(0, null, "Test2", "Test3", null, null, 0);
            driver.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(driver);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Personal number must be specified", e.getMessage());
        }
    }

    @Test
    public void add_3() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO(0, "Test1", null, "Test3", null, null, 0);
            driver.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(driver);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("First name must be specified", e.getMessage());
        }
    }

    @Test
    public void add_4() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO(0, "Test1", "Test2", null, null, null, 0);
            driver.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(driver);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Last name must be specified", e.getMessage());
        }
    }

    @Test
    public void add_5() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO(0, "Test1", "Test2", "Test3", null, null, 0);
            service.add(driver);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("City name must be specified", e.getMessage());
        }
    }

    @Test
    public void add_6() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO(0, "Тест1", "Test2", "Test3", null, null, 0);
            driver.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(driver);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong personal number", e.getMessage());
        }
    }

    @Test
    public void add_7() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO(0, "Test1", "Тест2", "Test3", null, null, 0);
            driver.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(driver);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong first name", e.getMessage());
        }
    }

    @Test
    public void add_8() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO(0, "Test1", "Test2", "Тест3", null, null, 0);
            driver.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(driver);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong last name", e.getMessage());
        }
    }

    @Test
    public void add_9() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO(0, "Test1", "Test2", "Test3", null, null, 0);
            driver.setLocation(new CityDTO(0, "ТестГород", 0, 0));
            service.add(driver);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong city name", e.getMessage());
        }
    }

    @Test
    public void add_10() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            when(dao.getDriverDAO().selectByPersNumber("Test1")).thenReturn(new Driver());
            DriverDTO driver = new DriverDTO(0, "Test1", "Test2", "Test3", null, null, 0);
            driver.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(driver);
            fail("Exception expected");
        } catch (EntityExistsException e) {
            assertEquals("Driver with personal number Test1 is already exists", e.getMessage());
        }
    }

    @Test
    public void change_1() throws Exception {
        Driver existsDriver = new Driver(2, "Test1", "Test2", "Test3", Driver.Status.REST, Timestamp.valueOf(LocalDateTime.now().minusDays(1L)), 0, null, null);
        City driverLocation = new City(2, "TestCity", 0, 0);
        Vehicle bindedVehicle = new Vehicle(2, "TestVehicle", 1000, Vehicle.Status.OK, null, null, driverLocation);
        existsDriver.setLocation(driverLocation);
        when(dao.getDriverDAO().selectByPersNumber("Test1")).thenReturn(existsDriver);
        when(dao.getCityDAO().selectByName("TestCity")).thenReturn(driverLocation);
        when(dao.getVehicleDAO().selectByRegNumber("TestVehicle")).thenReturn(bindedVehicle);

        DriverDTO driver = new DriverDTO(0, "Test1", "changed", "Test3", null, null, 0);
        service.change(driver);
    }

    @Test
    public void change_2() throws Exception {
        try {
            Driver existsDriver = new Driver(2, "Test1", "Test2", "Test3", Driver.Status.REST, Timestamp.valueOf(LocalDateTime.now().minusDays(1L)), 0, null, null);
            City driverLocation = new City(2, "TestCity", 0, 0);
            Vehicle bindedVehicle = new Vehicle(2, "TestVehicle", 1000, Vehicle.Status.OK, null, null, driverLocation);
            existsDriver.setLocation(driverLocation);
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(driverLocation);
            when(dao.getVehicleDAO().selectByRegNumber("TestVehicle")).thenReturn(bindedVehicle);

            DriverDTO driver = new DriverDTO(0, "Test1", "changed", "Test3", null, null, 0);
            service.change(driver);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("Driver with personal number Test1 not found", e.getMessage());
        }
    }

    @Test
    public void change_3() throws Exception {
        try {
            City driverLocation = new City(2, "TestCity", 0, 0);
            Vehicle bindedVehicle = new Vehicle(2, "TestVehicle", 1000, Vehicle.Status.OK, Arrays.asList(new Driver(), new Driver()), null, driverLocation);
            Driver existsDriver = new Driver(2, "Test1", "Test2", "Test3", Driver.Status.REST, Timestamp.valueOf(LocalDateTime.now().minusDays(1L)), 0, null, bindedVehicle);
            existsDriver.setLocation(driverLocation);
            when(dao.getDriverDAO().selectByPersNumber("Test1")).thenReturn(existsDriver);
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(driverLocation);
            when(dao.getVehicleDAO().selectByRegNumber("TestVehicle")).thenReturn(bindedVehicle);

            DriverDTO driver = new DriverDTO(0, "Test1", "Test2", "Test3", null, null, 0);
            driver.setLocation(new CityDTO(0, "TestAnotherCity", 0, 0));
            service.change(driver);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Attempted to move assigned driver", e.getMessage());
        }
    }

    @Test
    public void change_4() throws Exception {
        try {
            City driverLocation = new City(2, "TestCity", 0, 0);
            Vehicle bindedVehicle = new Vehicle(2, "TestVehicle", 1000, Vehicle.Status.OK, null, null, driverLocation);
            Driver existsDriver = new Driver(2, "Test1", "Test2", "Test3", Driver.Status.WORK, Timestamp.valueOf(LocalDateTime.now().minusDays(1L)), 0, null, bindedVehicle);
            existsDriver.setLocation(driverLocation);
            when(dao.getDriverDAO().selectByPersNumber("Test1")).thenReturn(existsDriver);
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(driverLocation);
            when(dao.getVehicleDAO().selectByRegNumber("TestVehicle")).thenReturn(bindedVehicle);

            DriverDTO driver = new DriverDTO(0, "Test1", "Test2", "Test3", null, null, 0);
            service.change(driver);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Attempted to unbind busy driver from vehicle", e.getMessage());
        }
    }
}
