package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.VehicleDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.VehicleService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private VehicleService service;

    @Autowired
    public void setService(VehicleService service) {
        this.service = service;
    }

    @Request
    @RequestMapping({"", "/"})
    public Response getAll() throws Exception {
            List<VehicleDTO> drivers = service.getAll();
            return new Response(200, "OK", drivers);
    }

    @Request
    @RequestMapping("/{regNumber}")
    public Response findByRegNumber(@PathVariable String regNumber) throws Exception {
            VehicleDTO vehicle = service.findByRegNumber(regNumber);
            return new Response(200, "OK", vehicle);
    }

    @Request
    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public Response add(@RequestBody String vehicleJson) throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            VehicleDTO vehicle = mapper.readValue(vehicleJson, VehicleDTO.class);
            service.add(vehicle);
            return new Response(200, "OK", null);
    }

    @Request
    @RequestMapping(path = {"", "/"}, method = RequestMethod.PUT)
    public Response change(@RequestBody String vehicleJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        VehicleDTO vehicle = mapper.readValue(vehicleJson, VehicleDTO.class);
        service.change(vehicle);
        return new Response(200, "OK", null);
    }
}
