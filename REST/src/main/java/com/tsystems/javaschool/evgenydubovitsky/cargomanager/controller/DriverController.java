package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.DriverService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private DriverService service;

    @Autowired
    public void setService(DriverService service) {
        this.service = service;
    }

    @Request
    @RequestMapping(path = {"", "/"})
    public Response getAll() throws Exception {
            List<DriverDTO> drivers = service.getAll();
            return new Response(200, "OK", drivers);
    }

    @Request
    @RequestMapping("/{persNumber}")
    public Response findByPersNumber(@PathVariable String persNumber) throws Exception {
            DriverDTO driver = service.findByPersNumber(persNumber);
            return new Response(200, "OK", driver);
    }

    @Request
    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public Response add(@RequestBody String driverJson) throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            DriverDTO driver = mapper.readValue(driverJson, DriverDTO.class);
            service.add(driver);
            return new Response(200, "OK", null);
    }

    @Request
    @RequestMapping(path = {"", "/"}, method = RequestMethod.PUT)
    public Response change(@RequestBody String driverJson) throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            DriverDTO driver = mapper.readValue(driverJson, DriverDTO.class);
            service.change(driver);
            return new Response(200, "OK", null);
    }
}
