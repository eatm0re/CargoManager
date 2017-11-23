package com.tsystems.javaschool.evgenydubovitsky.cargomanager;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.*;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.User;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Vehicle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class DAOTest {

    private CargoDAO cargoDAO;
    private CityDAO cityDAO;
    private DriverDAO driverDAO;
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
    public void setDriverDAO(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
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
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public void selectAllVehicles() throws Exception {
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
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public void selectUserById_1() throws Exception {
        User user = userDAO.selectById(1L);
        Assert.assertEquals(1L, user.getId());
        Assert.assertEquals("admin", user.getLogin());
        Assert.assertEquals("admin", user.getPassword());
        Assert.assertEquals(User.Post.STUFF, user.getPost());
    }

    @Test
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public void selectUserById_2() throws Exception {
        User user = userDAO.selectById(2L);
        Assert.assertEquals(2L, user.getId());
        Assert.assertEquals("user", user.getLogin());
        Assert.assertEquals("user", user.getPassword());
        Assert.assertEquals(User.Post.DRIVER, user.getPost());
    }

//    @Test
//    @Transactional(rollbackFor = Exception.class, readOnly = true)
//    public void selectDriversByFirstName() throws Exception {
//        List<Driver> drivers = driverDAO.selectByParam("firstName", "Alexandr");
//        Assert.assertTrue(drivers.size() >= 3);
//        Assert.assertEquals(4L, drivers.get(1).getId());
//        Assert.assertEquals("Moscow", drivers.get(0).getLocation().getName());
//        Assert.assertEquals("IOP357913", drivers.get(2).getPersNumber());
//        Assert.assertEquals("Alexandr", drivers.get(0).getFirstName());
//        Assert.assertEquals("Popov", drivers.get(1).getLastName());
//        Assert.assertEquals(Driver.Status.REST, drivers.get(2).getStatus());
//        Assert.assertTrue(drivers.get(0).getLastStatusUpdate().before(Timestamp.valueOf(LocalDateTime.now())));
//    }

//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    @Rollback
//    public void insertCity() throws Exception {
//        int count = cityDAO.selectByParam("name", "Test").size();
//        City city = new City();
//        city.setName("Test");
//        cityDAO.insert(city);
//        Assert.assertTrue(city.getId() > 0L);
//        Assert.assertEquals(count + 1, cityDAO.selectByParam("name", "Test").size());
//    }

//    @Test
//    @Transactional(rollbackFor = Exception.class)
//    @Rollback
//    public void changeCargo() throws Exception {
//        List<Cargo> oranges = cargoDAO.selectByParam("name", "Oranges");
//        List<Cargo> lemons = cargoDAO.selectByParam("name", "Lemons");
//        int orangesCount = oranges.size();
//        int lemonsCount = lemons.size();
//        oranges.get(0).setName("Lemons");
//        oranges = cargoDAO.selectByParam("name", "Oranges");
//        lemons = cargoDAO.selectByParam("name", "Lemons");
//        Assert.assertEquals(orangesCount - 1, oranges.size());
//        Assert.assertEquals(lemonsCount + 1, lemons.size());
//    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void updateCargo() throws Exception {
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
    public void deleteUser() throws Exception {
        int userCount = userDAO.selectAll().size();
        userDAO.deleteByParam("id", 1L);
        Assert.assertEquals(userCount - 1, userDAO.selectAll().size());
    }
}
