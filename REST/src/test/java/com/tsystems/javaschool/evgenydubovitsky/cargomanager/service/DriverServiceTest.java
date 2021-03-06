package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.OrderDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.VehicleDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Driver;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
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
public class DriverServiceTest {

    private ServiceFacade service;

    @Autowired
    public void setService(ServiceFacade service) {
        this.service = service;
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_5() throws Exception {
        try {
            service.getDriverService().change(new DriverDTO("LZX321098", null, null, null, new VehicleDTO("9876AS"), null));
            service.getDriverService().change(new DriverDTO("CVB765432", null, null, null, new VehicleDTO("9876AS"), null));
            service.getDriverService().change(new DriverDTO("NMQ109876", null, null, null, new VehicleDTO("9876AS"), null));
            service.getVehicleService().change(new VehicleDTO("9876AS", 0, null, null, new OrderDTO(2)));
            service.getDriverService().change(new DriverDTO("LZX321098", null, null, null, new VehicleDTO("9876AS"), Driver.Status.WORK));
            service.getDriverService().change(new DriverDTO("CVB765432", null, null, null, new VehicleDTO("9876AS"), Driver.Status.WORK));
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("Someone is already on wheel", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_6() throws Exception {
        try {
            service.getDriverService().change(new DriverDTO("LZX321098", null, null, null, null, Driver.Status.WORK));
            fail("Exception expected");
        } catch (BusinessException e) {
            assertEquals("To WORK status driver must be assigned to order", e.getMessage());
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_7() throws Exception {
        DriverDTO driver = new DriverDTO("LZX321098", null, null, null, new VehicleDTO("7890OP"), Driver.Status.REST);
        service.getDriverService().change(driver);
        assertEquals("7890OP", service.getDriverService().findByPersNumber("LZX321098").getVehicle().getRegNumber());
        assertEquals(1, service.getVehicleService().findByRegNumber("7890OP").getDrivers().size());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_8() throws Exception {
        DriverDTO driver = new DriverDTO("LZX321098", null, null, null, new VehicleDTO("7890OP"), Driver.Status.REST);
        service.getDriverService().change(driver);
        driver.setVehicle(null);
        service.getDriverService().change(driver);
        assertNull(service.getDriverService().findByPersNumber("LZX321098").getVehicle());
        assertEquals(0, service.getVehicleService().findByRegNumber("7890OP").getDrivers().size());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback
    public void change_9() throws Exception {
        service.getDriverService().change(new DriverDTO("LZX321098", null, null, null, new VehicleDTO("9876AS"), null));
        service.getDriverService().change(new DriverDTO("CVB765432", null, null, null, new VehicleDTO("9876AS"), null));
        service.getDriverService().change(new DriverDTO("NMQ109876", null, null, null, new VehicleDTO("9876AS"), null));
        service.getVehicleService().change(new VehicleDTO("9876AS", 0, null, null, new OrderDTO(2)));
        service.getDriverService().change(new DriverDTO("LZX321098", null, null, null, null, null));
        service.getDriverService().change(new DriverDTO("CVB765432", null, null, null, null, null));
        service.getDriverService().change(new DriverDTO("NMQ109876", null, null, null, null, null));
        assertNull(service.getVehicleService().findByRegNumber("9876AS").getOrder());
        assertNull(service.getOrderService().findById(2).getVehicle());
    }
}
