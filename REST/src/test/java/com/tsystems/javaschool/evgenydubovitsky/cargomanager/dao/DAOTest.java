package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.User;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Vehicle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
@WebAppConfiguration
public class DAOTest {

    private CargoDAO cargoDAO;
    private CityDAO cityDAO;
    private UserDAO userDAO;
    private VehicleDAO vehicleDAO;

    @Autowired
    public void setCargoDAO(CargoDAO cargoDAO) {
        this.cargoDAO = cargoDAO;
    }

    @Autowired
    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Test
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    public void selectAll_1() throws Exception {
        List<Vehicle> allVehicles = vehicleDAO.selectAll();
        Assert.assertTrue(allVehicles.size() >= 20);
        Vehicle vehicle = allVehicles.get(8);
        Assert.assertEquals(9L, vehicle.getId());
        Assert.assertEquals("Novosibirsk", vehicle.getLocation().getName());
        Assert.assertEquals("7654JK", vehicle.getRegNumber());
        Assert.assertEquals(7000L, vehicle.getCapacityKg());
        Assert.assertEquals(Vehicle.Status.BROKEN, vehicle.getStatus());
        Assert.assertEquals(Vehicle.Status.OK, allVehicles.get(0).getStatus());
    }

    @Test
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    public void selectById_1() throws Exception {
        User user = userDAO.selectById(1L);
        Assert.assertEquals(1L, user.getId());
        Assert.assertEquals("admin", user.getLogin());
        Assert.assertEquals("admin", user.getPassword());
        Assert.assertEquals(User.Post.STUFF, user.getPost());
    }

    @Test
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    public void selectById_2() throws Exception {
        User user = userDAO.selectById(2L);
        Assert.assertEquals(2L, user.getId());
        Assert.assertEquals("user", user.getLogin());
        Assert.assertEquals("user", user.getPassword());
        Assert.assertEquals(User.Post.DRIVER, user.getPost());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void update_1() throws Exception {
        Cargo cargo = new Cargo();
        cargo.setId(2);
        cargo.setName("Test");
        cargo.setLocation(cityDAO.selectById(1L));
        cargoDAO.update(cargo);
        cargo = cargoDAO.selectById(2L);
        Assert.assertEquals("Test", cargo.getName());
        Assert.assertEquals(1L, cargo.getLocation().getId());
        Assert.assertEquals(BigDecimal.ZERO, cargo.getWeightKg());
        Assert.assertEquals(Cargo.Status.READY, cargo.getStatus());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void deleteById_1() throws Exception {
        int userCount = userDAO.selectAll().size();
        userDAO.deleteById(1L);
        Assert.assertEquals(userCount - 1, userDAO.selectAll().size());
    }
}
