package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/driver")
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
        } catch (RuntimeException e) {
            return new Response(500, "Error", e.getMessage());
        }
    }

    @RequestMapping("/{persNumber}")
    public Response findByPersNumber(@PathVariable String persNumber) {
        try {
            DriverDTO driver = service.findByPersNumber(persNumber);
            return new Response(200, "OK", driver);
        } catch (RuntimeException e) {
            return new Response(500, "Error", e.getMessage());
        }
    }
}
