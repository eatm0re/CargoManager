package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacade;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacadeMock;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.City;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.EntityExistsException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.EntityNotFoundException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.MissedParameterException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.WrongParameterException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl.CityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
@WebAppConfiguration
public class CityServiceMockTest {

    private DAOFacade dao;
    private CityServiceImpl service;

    @Before
    public void setUp() throws Exception {
        dao = new DAOFacadeMock();
        service = new CityServiceImpl();
        service.setDao(dao);
    }

    @Test
    public void add_1() throws Exception {
        CityDTO city = new CityDTO("TestCity");
        service.add(city);
    }

    @Test
    public void add_2() throws Exception {
        try {
            CityDTO city = new CityDTO("");
            service.add(city);
            fail("Exception expected");
        } catch (MissedParameterException e) {
            assertEquals("City name must be specified", e.getMessage());
        }
    }

    @Test
    public void add_3() throws Exception {
        try {
            CityDTO city = new CityDTO("ТестГород");
            service.add(city);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong city name", e.getMessage());
        }
    }

    @Test
    public void add_4() throws Exception {
        try {
            when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
            CityDTO city = new CityDTO("TestCity");
            service.add(city);
            fail("Exception expected");
        } catch (EntityExistsException e) {
            assertEquals("City TestCity is already exists", e.getMessage());
        }
    }

    @Test
    public void add_5() throws Exception {
        try {
            CityDTO city = new CityDTO("TestCity", 100, 0);
            service.add(city);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("City latitude must be between -90 and +90", e.getMessage());
        }
    }

    @Test
    public void add_6() throws Exception {
        try {
            CityDTO city = new CityDTO("TestCity", 0, -200);
            service.add(city);
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("City longitude must be between -180 (not including) and +180 (including)", e.getMessage());
        }
    }

    @Test
    public void change_1() throws Exception {
        when(dao.getCityDAO().selectByName("TestCity")).thenReturn(new City());
        CityDTO city = new CityDTO("TestCity");
        service.change(city);
    }

    @Test
    public void change_2() throws Exception {
        try {
            CityDTO city = new CityDTO("TestCity");
            service.change(city);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("City TestCity not found", e.getMessage());
        }
    }
}
