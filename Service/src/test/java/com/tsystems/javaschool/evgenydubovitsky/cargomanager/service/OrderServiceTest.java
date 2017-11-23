package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.*;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Cargo;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Driver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class OrderServiceTest {

    private ServiceFacade service;

    @Autowired
    public void setService(ServiceFacade service) {
        this.service = service;
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_1() throws Exception {
        OrderDTO order = new OrderDTO(0, 0, 0, Arrays.asList(
                new CheckpointDTO(0, null, new CityDTO(0, "Saint-Petersburg", 0, 0), Arrays.asList(
                        new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                )),
                new CheckpointDTO(0, null, new CityDTO(0, "Novosibirsk", 0, 0), Arrays.asList(
                        new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                ))
        ), null);

        long orderId = service.getOrderService().add(order);
        order = service.getOrderService().findById(orderId);
        assertEquals(2, order.getTotal());
        assertEquals(2, order.getCheckpoints().size());
        assertNull(order.getCheckpoints().get(0).getOrder());
        assertNull(order.getCheckpoints().get(1).getCity());
        assertNull(order.getCheckpoints().get(0).getTasks());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_2() throws Exception {
        try {
            OrderDTO order = new OrderDTO(0, 0, 0, null, null);
            service.getOrderService().add(order);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Order must have at least 2 checkpoints", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_3() throws Exception {
        try {
            OrderDTO order = new OrderDTO(0, 0, 0, Arrays.asList(
                    new CheckpointDTO(0, null, new CityDTO(0, "Saint-Petersburg", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    )),
                    new CheckpointDTO(0, null, new CityDTO(0, null, 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    ))
            ), null);

            service.getOrderService().add(order);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("City name must be specified in every checkpoint", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_4() throws Exception {
        try {
            OrderDTO order = new OrderDTO(0, 0, 0, Arrays.asList(
                    new CheckpointDTO(0, null, new CityDTO(0, "Saint-Petersburg", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    )),
                    new CheckpointDTO(0, null, new CityDTO(0, "Новосибирск", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    ))
            ), null);

            service.getOrderService().add(order);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong city name", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_5() throws Exception {
        try {
            OrderDTO order = new OrderDTO(0, 0, 0, Arrays.asList(
                    new CheckpointDTO(0, null, new CityDTO(0, "Saint-Petersburg", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    )),
                    new CheckpointDTO(0, null, new CityDTO(0, "TestCity", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    ))
            ), null);

            service.getOrderService().add(order);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("City TestCity not found", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_6() throws Exception {
        try {
            OrderDTO order = new OrderDTO(0, 0, 0, Arrays.asList(
                    new CheckpointDTO(0, null, new CityDTO(0, "Saint-Petersburg", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    )),
                    new CheckpointDTO(0, null, new CityDTO(0, "Novosibirsk", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(-4, null, null, null))
                    ))
            ), null);

            service.getOrderService().add(order);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Cargo ID must be positive", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_7() throws Exception {
        try {
            OrderDTO order = new OrderDTO(0, 0, 0, Arrays.asList(
                    new CheckpointDTO(0, null, new CityDTO(0, "Saint-Petersburg", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    )),
                    new CheckpointDTO(0, null, new CityDTO(0, "Novosibirsk", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(6666666666666L, null, null, null))
                    ))
            ), null);

            service.getOrderService().add(order);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("Cargo #6666666666666 not found", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_8() throws Exception {
        try {
            OrderDTO order = new OrderDTO(0, 0, 0, Arrays.asList(
                    new CheckpointDTO(0, null, new CityDTO(0, "Saint-Petersburg", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    )),
                    new CheckpointDTO(0, null, new CityDTO(0, "Saint-Petersburg", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    ))
            ), null);

            service.getOrderService().add(order);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Unloading cargo in city it was loaded", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_9() throws Exception {
        try {
            OrderDTO order = new OrderDTO(0, 0, 0, Arrays.asList(
                    new CheckpointDTO(0, null, new CityDTO(0, "Moscow", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    )),
                    new CheckpointDTO(0, null, new CityDTO(0, "Novosibirsk", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    ))
            ), null);

            service.getOrderService().add(order);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Loading cargo when it is in another city", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void add_10() throws Exception {
        try {
            OrderDTO order = new OrderDTO(0, 0, 0, Arrays.asList(
                    new CheckpointDTO(0, null, new CityDTO(0, "Saint-Petersburg", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null)),
                            new TaskDTO(0, null, new CargoDTO(5, null, null, null))
                    )),
                    new CheckpointDTO(0, null, new CityDTO(0, "Novosibirsk", 0, 0), Arrays.asList(
                            new TaskDTO(0, null, new CargoDTO(4, null, null, null))
                    ))
            ), null);

            service.getOrderService().add(order);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("All loaded cargoes must be unloaded", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void progressReport_1() throws Exception {
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getOrderService().progressReport(2);
        service.getOrderService().progressReport(2);
        OrderDTO order = service.getOrderService().findById(2);
        assertEquals(2, order.getProgress());
        VehicleDTO vehicle = service.getVehicleService().findByRegNumber("9876AS");
        assertEquals("Moscow", vehicle.getLocation().getName());
        for (DriverDTO driver : vehicle.getDrivers()) {
            driver = service.getDriverService().findByPersNumber(driver.getPersNumber());
            assertEquals("Moscow", driver.getLocation().getName());
            assertEquals(Driver.Status.REST, driver.getStatus());
        }
        assertTrue(service.getDriverService().findByPersNumber("LZX321098").getWorkedThisMonth() > 0);
        assertEquals(0, service.getDriverService().findByPersNumber("CVB765432").getWorkedThisMonth());
        CargoDTO cargo = service.getCargoService().findById(5);
        assertEquals(Cargo.Status.SHIPPED, cargo.getStatus());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void progressReport_2() throws Exception {
        try {
            service.getOrderService().progressReport(-2);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Order ID must be positive", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void progressReport_3() throws Exception {
        try {
            service.getOrderService().progressReport(666_666_666_666L);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("Order #666666666666 not found", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void progressReport_4() throws Exception {
        try {
            service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
            service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            for (int i = 0; i < 5; i++) {
                service.getOrderService().progressReport(2);
            }
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Order #2 is already completed", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void progressReport_5() throws Exception {
        try {
            service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getOrderService().progressReport(2);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("No vehicle assigned to order", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void progressReport_6() throws Exception {
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        for (int i = 0; i < 4; i++) {
            service.getOrderService().progressReport(2);
        }
        OrderDTO order = service.getOrderService().findById(2);
        assertNull(order.getVehicle());
        VehicleDTO vehicle = service.getVehicleService().findByRegNumber("9876AS");
        assertNull(vehicle.getOrder());
        for (DriverDTO driver : vehicle.getDrivers()) {
            driver = service.getDriverService().findByPersNumber(driver.getPersNumber());
            assertEquals("9876AS", driver.getVehicle().getRegNumber());
            assertEquals("Ekaterinburg", driver.getLocation().getName());
            assertEquals(Driver.Status.REST, driver.getStatus());
        }
        assertTrue(service.getDriverService().findByPersNumber("LZX321098").getWorkedThisMonth() > 0);
        assertEquals(0, service.getDriverService().findByPersNumber("CVB765432").getWorkedThisMonth());
        for (CheckpointDTO checkpoint : order.getCheckpoints()) {
            checkpoint = service.getCheckpointService().findById(checkpoint.getId());
            for (TaskDTO task : checkpoint.getTasks()) {
                task = service.getTaskService().findById(task.getId());
                CargoDTO cargo = service.getCargoService().findById(task.getCargo().getId());
                assertNotEquals("Saint-Petersburg", cargo.getLocation().getName());
                assertEquals(Cargo.Status.DELIVERED, cargo.getStatus());
            }
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_1() throws Exception {
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getOrderService().progressReport(2);
        service.getOrderService().progressReport(2);
        service.getOrderService().interrupt(2);
        OrderDTO order = service.getOrderService().findById(2);
        assertEquals(2, order.getProgress());
        VehicleDTO vehicle = service.getVehicleService().findByRegNumber("9876AS");
        assertNull(vehicle.getOrder());
        assertNull(order.getVehicle());
        assertEquals("Moscow", vehicle.getLocation().getName());
        for (DriverDTO driver : vehicle.getDrivers()) {
            driver = service.getDriverService().findByPersNumber(driver.getPersNumber());
            assertEquals("Moscow", driver.getLocation().getName());
            assertEquals(Driver.Status.REST, driver.getStatus());
        }
        assertTrue(service.getDriverService().findByPersNumber("LZX321098").getWorkedThisMonth() > 0);
        assertEquals(0, service.getDriverService().findByPersNumber("CVB765432").getWorkedThisMonth());
        CargoDTO firstCargo = service.getCargoService().findById(4);
        CargoDTO secondCargo = service.getCargoService().findById(5);
        CargoDTO thirdCargo = service.getCargoService().findById(7);
        assertEquals(Cargo.Status.DELIVERED, firstCargo.getStatus());
        assertEquals(Cargo.Status.READY, secondCargo.getStatus());
        assertEquals(Cargo.Status.READY, thirdCargo.getStatus());
        assertEquals("Moscow", firstCargo.getLocation().getName());
        assertEquals("Moscow", secondCargo.getLocation().getName());
        assertEquals("Moscow", thirdCargo.getLocation().getName());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_2() throws Exception {
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getOrderService().interrupt(2);
        OrderDTO order = service.getOrderService().findById(2);
        assertEquals(0, order.getProgress());
        VehicleDTO vehicle = service.getVehicleService().findByRegNumber("9876AS");
        assertNull(vehicle.getOrder());
        assertNull(order.getVehicle());
        assertEquals("Saint-Petersburg", vehicle.getLocation().getName());
        for (DriverDTO driver : vehicle.getDrivers()) {
            driver = service.getDriverService().findByPersNumber(driver.getPersNumber());
            assertEquals("Saint-Petersburg", driver.getLocation().getName());
            assertEquals(Driver.Status.REST, driver.getStatus());
        }
        assertTrue(service.getDriverService().findByPersNumber("LZX321098").getWorkedThisMonth() > 0);
        assertEquals(0, service.getDriverService().findByPersNumber("CVB765432").getWorkedThisMonth());
        CargoDTO firstCargo = service.getCargoService().findById(4);
        CargoDTO secondCargo = service.getCargoService().findById(5);
        CargoDTO thirdCargo = service.getCargoService().findById(7);
        assertEquals(Cargo.Status.READY, firstCargo.getStatus());
        assertEquals(Cargo.Status.READY, secondCargo.getStatus());
        assertEquals(Cargo.Status.READY, thirdCargo.getStatus());
        assertEquals("Saint-Petersburg", firstCargo.getLocation().getName());
        assertEquals("Saint-Petersburg", secondCargo.getLocation().getName());
        assertEquals("Moscow", thirdCargo.getLocation().getName());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_3() throws Exception {
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getOrderService().progressReport(2);
        service.getOrderService().interrupt(2);
        OrderDTO order = service.getOrderService().findById(2);
        assertEquals(0, order.getProgress());
        VehicleDTO vehicle = service.getVehicleService().findByRegNumber("9876AS");
        assertNull(vehicle.getOrder());
        assertNull(order.getVehicle());
        assertEquals("Saint-Petersburg", vehicle.getLocation().getName());
        for (DriverDTO driver : vehicle.getDrivers()) {
            driver = service.getDriverService().findByPersNumber(driver.getPersNumber());
            assertEquals("Saint-Petersburg", driver.getLocation().getName());
            assertEquals(Driver.Status.REST, driver.getStatus());
        }
        assertTrue(service.getDriverService().findByPersNumber("LZX321098").getWorkedThisMonth() > 0);
        assertEquals(0, service.getDriverService().findByPersNumber("CVB765432").getWorkedThisMonth());
        CargoDTO firstCargo = service.getCargoService().findById(4);
        CargoDTO secondCargo = service.getCargoService().findById(5);
        CargoDTO thirdCargo = service.getCargoService().findById(7);
        assertEquals(Cargo.Status.READY, firstCargo.getStatus());
        assertEquals(Cargo.Status.READY, secondCargo.getStatus());
        assertEquals(Cargo.Status.READY, thirdCargo.getStatus());
        assertEquals("Saint-Petersburg", firstCargo.getLocation().getName());
        assertEquals("Saint-Petersburg", secondCargo.getLocation().getName());
        assertEquals("Moscow", thirdCargo.getLocation().getName());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_4() throws Exception {
        try {
            service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
            service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getOrderService().progressReport(2);
            service.getOrderService().interrupt(2);
            service.getOrderService().progressReport(2);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("No vehicle assigned to order", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_5() throws Exception {
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getOrderService().progressReport(2);
        service.getOrderService().interrupt(2);
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getOrderService().progressReport(2);
        service.getOrderService().progressReport(2);
        OrderDTO order = service.getOrderService().findById(2);
        assertEquals(2, order.getProgress());
        VehicleDTO vehicle = service.getVehicleService().findByRegNumber("9876AS");
        assertEquals("Moscow", vehicle.getLocation().getName());
        for (DriverDTO driver : vehicle.getDrivers()) {
            driver = service.getDriverService().findByPersNumber(driver.getPersNumber());
            assertEquals("Moscow", driver.getLocation().getName());
            assertEquals(Driver.Status.REST, driver.getStatus());
        }
        assertTrue(service.getDriverService().findByPersNumber("LZX321098").getWorkedThisMonth() > 0);
        assertEquals(0, service.getDriverService().findByPersNumber("CVB765432").getWorkedThisMonth());
        CargoDTO firstCargo = service.getCargoService().findById(4);
        CargoDTO secondCargo = service.getCargoService().findById(5);
        CargoDTO thirdCargo = service.getCargoService().findById(7);
        assertEquals(Cargo.Status.DELIVERED, firstCargo.getStatus());
        assertEquals(Cargo.Status.SHIPPED, secondCargo.getStatus());
        assertEquals(Cargo.Status.SHIPPED, thirdCargo.getStatus());
        assertEquals("Moscow", firstCargo.getLocation().getName());
        assertEquals("Saint-Petersburg", secondCargo.getLocation().getName());
        assertEquals("Moscow", thirdCargo.getLocation().getName());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_6() throws Exception {
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getOrderService().progressReport(2);
        service.getOrderService().progressReport(2);
        service.getOrderService().interrupt(2);
        OrderDTO order = service.getOrderService().findById(2);
        assertEquals(2, order.getProgress());
        VehicleDTO vehicle = service.getVehicleService().findByRegNumber("9876AS");
        assertNull(vehicle.getOrder());
        assertNull(order.getVehicle());
        assertEquals("Moscow", vehicle.getLocation().getName());
        for (DriverDTO driver : vehicle.getDrivers()) {
            driver = service.getDriverService().findByPersNumber(driver.getPersNumber());
            assertEquals("Moscow", driver.getLocation().getName());
            assertEquals(Driver.Status.REST, driver.getStatus());
        }
        assertTrue(service.getDriverService().findByPersNumber("LZX321098").getWorkedThisMonth() > 0);
        assertEquals(0, service.getDriverService().findByPersNumber("CVB765432").getWorkedThisMonth());
        CargoDTO firstCargo = service.getCargoService().findById(4);
        CargoDTO secondCargo = service.getCargoService().findById(5);
        CargoDTO thirdCargo = service.getCargoService().findById(7);
        assertEquals(Cargo.Status.DELIVERED, firstCargo.getStatus());
        assertEquals(Cargo.Status.READY, secondCargo.getStatus());
        assertEquals(Cargo.Status.READY, thirdCargo.getStatus());
        assertEquals("Moscow", firstCargo.getLocation().getName());
        assertEquals("Moscow", secondCargo.getLocation().getName());
        assertEquals("Moscow", thirdCargo.getLocation().getName());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_7() throws Exception {
        try {
            service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
            service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
            for (int i = 0; i < 4; i++) {
                service.getOrderService().progressReport(2);
            }
            service.getOrderService().interrupt(2);
            fail("Exception expected");
        } catch (IllegalStateException e) {
            assertEquals("Order #2 is already completed", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_8() throws Exception {
        try {
            service.getOrderService().interrupt(-2);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Order ID must be positive", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_9() throws Exception {
        try {
            service.getOrderService().interrupt(666_666_666_666L);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("Order #666666666666 not found", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void interrupt_10() throws Exception {
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "CVB765432", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getDriverService().change(new DriverDTO(0, "NMQ109876", null, null, null, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getDriverService().change(new DriverDTO(0, "LZX321098", null, null, Driver.Status.WORK, null, 0, null, new VehicleDTO(0, "9876AS", 0, null)));
        service.getOrderService().progressReport(2);
        service.getOrderService().interrupt(2);
        service.getVehicleService().change(new VehicleDTO(0, "9876AS", 0, null, null, new OrderDTO(2, 0, 0), null));
        service.getOrderService().progressReport(2);
        OrderDTO order = service.getOrderService().findById(2);
        assertEquals(1, order.getProgress());
        VehicleDTO vehicle = service.getVehicleService().findByRegNumber("9876AS");
        assertEquals("Saint-Petersburg", vehicle.getLocation().getName());
        for (DriverDTO driver : vehicle.getDrivers()) {
            driver = service.getDriverService().findByPersNumber(driver.getPersNumber());
            assertEquals("Saint-Petersburg", driver.getLocation().getName());
            assertEquals(Driver.Status.REST, driver.getStatus());
        }
        assertTrue(service.getDriverService().findByPersNumber("LZX321098").getWorkedThisMonth() > 0);
        assertEquals(0, service.getDriverService().findByPersNumber("CVB765432").getWorkedThisMonth());
        CargoDTO firstCargo = service.getCargoService().findById(4);
        CargoDTO secondCargo = service.getCargoService().findById(5);
        CargoDTO thirdCargo = service.getCargoService().findById(7);
        assertEquals(Cargo.Status.SHIPPED, firstCargo.getStatus());
        assertEquals(Cargo.Status.SHIPPED, secondCargo.getStatus());
        assertEquals(Cargo.Status.READY, thirdCargo.getStatus());
        assertEquals("Saint-Petersburg", firstCargo.getLocation().getName());
        assertEquals("Saint-Petersburg", secondCargo.getLocation().getName());
        assertEquals("Moscow", thirdCargo.getLocation().getName());
    }

    @Test
    public void capacityNeeded_1() throws Exception {
        assertEquals(5700, service.getOrderService().capacityNeeded(1));
    }

    @Test
    public void capacityNeeded_2() throws Exception {
        assertEquals(3700, service.getOrderService().capacityNeeded(2));
    }

    @Test
    public void capacityNeeded_3() throws Exception {
        try {
            service.getOrderService().capacityNeeded(-2);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Order ID must be positive", e.getMessage());
        }
    }

    @Test
    public void capacityNeeded_4() throws Exception {
        try {
            service.getOrderService().capacityNeeded(666_666_666_666L);
            fail("Exception expected");
        } catch (EntityNotFoundException e) {
            assertEquals("Order #666666666666 not found", e.getMessage());
        }
    }
}
