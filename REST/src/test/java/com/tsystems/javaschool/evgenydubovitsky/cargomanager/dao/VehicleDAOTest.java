package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
@WebAppConfiguration
public class VehicleDAOTest {

    private VehicleDAO dao;

    @Autowired
    public void setDao(VehicleDAO dao) {
        this.dao = dao;
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void deleteByRegNumber_1() throws Exception {
        assertTrue(dao.deleteByRegNumber("7890OP"));
        assertNull(dao.selectByRegNumber("7890OP"));
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void deleteByRegNumber_2() throws Exception {
        assertFalse(dao.deleteByRegNumber("lol"));
    }
}
