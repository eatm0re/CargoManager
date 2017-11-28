package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring.xml"})
@WebAppConfiguration
public class EntitiesTest {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Test
    public void readCargo() {
        try (Session session = sessionFactory.openSession()) {
            Cargo cargo = session.get(Cargo.class, 2L);
            Assert.assertEquals(2L, cargo.getId());
            Assert.assertEquals("Oranges", cargo.getName());
            Assert.assertEquals(1500.0, cargo.getWeightKg().doubleValue(), 0.0001);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllCities() {
        try (Session session = sessionFactory.openSession()) {
            List<City> allCities = session.createQuery("from City").list();
            Assert.assertTrue(allCities.size() >= 17);
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public void addChangeDeleteDriver() {
        Session session = sessionFactory.getCurrentSession();

        // check absence
        List<Driver> res = session.createQuery("from Driver x where x.persNumber = ?1")
                .setParameter(1, "TEST")
                .list();
        Assert.assertTrue(res.size() == 0);

        // add new driver
        Driver driver = new Driver();
        driver.setPersNumber("TEST");
        driver.setFirstName("Test_1");
        driver.setLastName("Test_2");
        driver.setLocation(session.get(City.class, 1L));
        session.save(driver);

        // check addition
        driver = (Driver) session.createQuery("from Driver x where x.persNumber = ?1")
                .setParameter(1, "TEST")
                .list()
                .get(0);
        Assert.assertEquals("TEST", driver.getPersNumber());
        Assert.assertEquals("Test_1", driver.getFirstName());
        Assert.assertEquals("Test_2", driver.getLastName());
        Assert.assertEquals("Moscow", driver.getLocation().getName());
        Assert.assertEquals(Driver.Status.REST, driver.getStatus());
        Assert.assertNull(driver.getVehicle());
        Assert.assertEquals(0, driver.getWorkedThisMonthMs());
        Timestamp time = driver.getLastStatusUpdate();
        Timestamp past = Timestamp.valueOf(LocalDateTime.now().minusSeconds(20));
        Assert.assertTrue(time.after(past));

        // change driver
        driver.setLocation(session.get(City.class, 2L));
        session.update(driver);

        // check changed driver
        driver = (Driver) session.createQuery("from Driver x where x.persNumber = ?1")
                .setParameter(1, "TEST")
                .list()
                .get(0);
        Assert.assertEquals("Saint-Petersburg", driver.getLocation().getName());

        // remove driver
        session.delete(driver);

        // check deletion
        res = session.createQuery("from Driver x where x.persNumber = ?1")
                .setParameter(1, "TEST")
                .list();
        Assert.assertTrue(res.size() == 0);
    }
}
