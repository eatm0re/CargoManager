package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacade;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacadeMock;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.*;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl.DriverServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
@WebAppConfiguration
public class DriverServiceMockTest {

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
        DriverDTO driver = new DriverDTO("Test1", "Test2", "Test3", new CityDTO("TestCity"));
        service.add(driver);
    }

    @Test
    public void add_2() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO(null, "Test2", "Test3", new CityDTO("TestCity"));
            service.add(driver);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("Personal number must be specified", e.getMessage());
        }
    }

    @Test
    public void add_3() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO("Test1", null, "Test3", new CityDTO("TestCity"));
            service.add(driver);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("First name must be specified", e.getMessage());
        }
    }

    @Test
    public void add_4() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO("Test1", "Test2", null, new CityDTO("TestCity"));
            service.add(driver);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("Last name must be specified", e.getMessage());
        }
    }

    @Test
    public void add_5() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO("Test1", "Test2", "Test3", null);
            service.add(driver);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("City name must be specified", e.getMessage());
        }
    }

    @Test
    public void add_6() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO("Тест1", "Test2", "Test3", new CityDTO("TestCity"));
            service.add(driver);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong personal number", e.getMessage());
        }
    }

    @Test
    public void add_7() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO("Test1", "Тест2", "Test3", new CityDTO("TestCity"));
            service.add(driver);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong first name", e.getMessage());
        }
    }

    @Test
    public void add_8() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO("Test1", "Test2", "Тест3", new CityDTO("TestCity"));
            service.add(driver);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong last name", e.getMessage());
        }
    }

    @Test
    public void add_9() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            DriverDTO driver = new DriverDTO("Test1", "Test2", "Test3", new CityDTO("ТестГород"));
            service.add(driver);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong city name", e.getMessage());
        }
    }

    @Test
    public void add_10() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            when(dao.getDriverDAO().selectByPersNumber("Test1")).thenReturn(new Driver());
            DriverDTO driver = new DriverDTO("Test1", "Test2", "Test3", new CityDTO("TestCity"));
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

        DriverDTO driver = new DriverDTO("Test1", "changed", "Test3", null, null, null);
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

            DriverDTO driver = new DriverDTO("Test1", "changed", "Test3", null, null, null);
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

            DriverDTO driver = new DriverDTO("Test1", "Test2", "Test3", new CityDTO("TestAnotherCity"), null, null);
            service.change(driver);
            fail("Exception expected");
        } catch (BusinessException e) {
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

            DriverDTO driver = new DriverDTO("Test1", "Test2", "Test3", null, null, null);
            service.change(driver);
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Attempted to unbind busy driver from vehicle", e.getMessage());
        }
    }
}
