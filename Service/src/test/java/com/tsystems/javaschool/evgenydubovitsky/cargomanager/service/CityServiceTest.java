package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class CityServiceTest {

    private ServiceFacade service;

    @Autowired
    public void setService(ServiceFacade service) {
        this.service = service;
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
