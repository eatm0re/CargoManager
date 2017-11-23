package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacade;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacadeMock;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CargoDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl.CargoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class CargoServiceTest {

    private DAOFacade dao;
    private CargoServiceImpl service;

    @Before
    public void setUp() throws Exception {
        dao = new DAOFacadeMock();
        service = new CargoServiceImpl();
        service.setDao(dao);
    }

    @Test
    public void add_1() throws Exception {
        when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
        CargoDTO cargo = new CargoDTO(0, "Test", BigDecimal.ONE, null);
        cargo.setLocation(new CityDTO(0, "TestCity", 0, 0));
        service.add(cargo);
    }

    @Test
    public void add_2() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            CargoDTO cargo = new CargoDTO(0, null, BigDecimal.ONE, null);
            cargo.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(cargo);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Cargo name must be specified", e.getMessage());
        }
    }

    @Test
    public void add_3() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            CargoDTO cargo = new CargoDTO(0, "Тест", BigDecimal.ONE, null);
            cargo.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(cargo);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong cargo name", e.getMessage());
        }
    }

    @Test
    public void add_4() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            CargoDTO cargo = new CargoDTO(0, "Test", BigDecimal.ONE.negate(), null);
            cargo.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(cargo);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Weight must be non-negative", e.getMessage());
        }
    }

    @Test
    public void add_5() throws Exception {
        try {
            CargoDTO cargo = new CargoDTO(0, "Test", BigDecimal.ONE, null);
            service.add(cargo);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("City name must be specified", e.getMessage());
        }
    }

    @Test
    public void add_6() throws Exception {
        try {
            CargoDTO cargo = new CargoDTO(0, "Test", BigDecimal.ONE, null);
            cargo.setLocation(new CityDTO(0, "Test@City", 0, 0));
            service.add(cargo);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong city name", e.getMessage());
        }
    }

    @Test
    public void add_7() throws Exception {
        try {
            CargoDTO cargo = new CargoDTO(0, "Test", BigDecimal.ONE, null);
            cargo.setLocation(new CityDTO(0, "TestCity", 0, 0));
            service.add(cargo);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("City TestCity not found", e.getMessage());
        }
    }

    @Test
    public void change_1() throws Exception {
        when(dao.getCargoDAO().selectById(2L)).thenReturn(new Cargo(2L, "Test", BigDecimal.ONE, Cargo.Status.READY));
        CargoDTO cargo = new CargoDTO(2L, "Test", BigDecimal.ONE, null);
        service.change(cargo);
    }

    @Test
    public void change_2() throws Exception {
        try {
            CargoDTO cargo = new CargoDTO(-2L, "Test", BigDecimal.ONE, null);
            service.change(cargo);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Cargo ID must be positive", e.getMessage());
        }
    }

    @Test
    public void change_3() throws Exception {
        try {
            CargoDTO cargo = new CargoDTO(2L, "Test", BigDecimal.ONE, null);
            service.change(cargo);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("Cargo #2 not found", e.getMessage());
        }
    }

    @Test
    public void change_4() throws Exception {
        try {
            when(dao.getCargoDAO().selectById(2L)).thenReturn(new Cargo(2L, "Test", BigDecimal.ONE, Cargo.Status.READY));
            CargoDTO cargo = new CargoDTO(2L, "Тест", BigDecimal.ONE, null);
            service.change(cargo);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong cargo name", e.getMessage());
        }
    }

    @Test
    public void change_5() throws Exception {
        try {
            when(dao.getCargoDAO().selectById(2L)).thenReturn(new Cargo(2L, "Test", BigDecimal.ONE, Cargo.Status.READY));
            CargoDTO cargo = new CargoDTO(2L, "Test", BigDecimal.ONE.negate(), null);
            service.change(cargo);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Weight must be non-negative", e.getMessage());
        }
    }
}
