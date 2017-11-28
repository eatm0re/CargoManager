package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.EntityNotFoundException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.WrongParameterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
@WebAppConfiguration
public class CityServiceTest {

    private ServiceFacade service;

    @Autowired
    public void setService(ServiceFacade service) {
        this.service = service;
    }

    @Test
    public void findByName_1() throws Exception {
        assertEquals("Saint-Petersburg", service.getCityService().findByName("Saint-Petersburg").getName());
    }

    @Test
    public void findByName_2() throws Exception {
        try {
            service.getCityService().findByName("lol");
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("City lol not found", e.getMessage());
        }
    }

    @Test
    public void findByName_3() throws Exception {
        try {
            service.getCityService().findByName("лол");
            fail("Exception expected");
        } catch (WrongParameterException e) {
            assertEquals("Wrong city name", e.getMessage());
        }
    }

    @Test
    public void distance_1() throws Exception {
        assertEquals(730.261, service.getCityService().distance("Moscow", "Saint-Petersburg"), 1);
    }

    @Test
    public void distance_2() throws Exception {
        assertEquals(4257.211, service.getCityService().distance("Rostov", "Krasnoyarsk"), 1);
    }
}
