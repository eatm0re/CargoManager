package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private DriverService service;

    @Autowired
    public void setService(DriverService service) {
        this.service = service;
    }

    @RequestMapping(path = {"", "/"})
    public Response getAll() {
        try {
            List<DriverDTO> drivers = service.getAll();
            return new Response(200, "OK", drivers);
        } catch (BusinessException e) {
            return new Response(e.getHttpCode(), e.getMessage(), e.getClass().getSimpleName());
        } catch (PersistenceException e) {
            return new Response(503, "Can not access the database. Please try again later or contact your administrator", e.getMessage());
        } catch (Exception e) {
            return new Response(520, "Unknown server error. Please contact your administrator", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @RequestMapping("/{persNumber}")
    public Response findByPersNumber(@PathVariable String persNumber) {
        try {
            DriverDTO driver = service.findByPersNumber(persNumber);
            return new Response(200, "OK", driver);
        } catch (BusinessException e) {
            return new Response(e.getHttpCode(), e.getMessage(), e.getClass().getSimpleName());
        } catch (PersistenceException e) {
            return new Response(503, "Can not access the database. Please try again later or contact your administrator", e.getMessage());
        } catch (Exception e) {
            return new Response(520, "Unknown server error. Please contact your administrator", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public Response add(@RequestBody String driverJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DriverDTO driver = mapper.readValue(driverJson, DriverDTO.class);
            service.add(driver);
            return new Response(200, "OK", null);
        } catch (BusinessException e) {
            return new Response(e.getHttpCode(), e.getMessage(), e.getClass().getSimpleName());
        } catch (PersistenceException e) {
            return new Response(503, "Can not access the database. Please try again later or contact your administrator", e.getMessage());
        } catch (Exception e) {
            return new Response(520, "Unknown server error. Please contact your administrator", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.PUT)
    public Response change(@RequestBody String driverJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DriverDTO driver = mapper.readValue(driverJson, DriverDTO.class);
            service.change(driver);
            return new Response(200, "OK", null);
        } catch (BusinessException e) {
            return new Response(e.getHttpCode(), e.getMessage(), e.getClass().getSimpleName());
        } catch (PersistenceException e) {
            return new Response(503, "Can not access the database. Please try again later or contact your administrator", e.getMessage());
        } catch (Exception e) {
            return new Response(520, "Unknown server error. Please contact your administrator", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
