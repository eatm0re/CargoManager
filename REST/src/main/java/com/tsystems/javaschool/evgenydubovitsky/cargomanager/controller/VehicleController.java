package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.VehicleDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private VehicleService service;

    @Autowired
    public void setService(VehicleService service) {
        this.service = service;
    }

    @RequestMapping({"", "/"})
    public Response getAll() {
        try {
            List<VehicleDTO> drivers = service.getAll();
            return new Response(200, "OK", drivers);
        } catch (BusinessException e) {
            return new Response(e.getHttpCode(), e.getMessage(), e.getClass().getSimpleName());
        } catch (PersistenceException e) {
            return new Response(503, "Can not access the database. Please try again later or contact your administrator", e.getMessage());
        } catch (Exception e) {
            return new Response(520, "Unknown server error. Please contact your administrator", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @RequestMapping("/{regNumber}")
    public Response findByRegNumber(@PathVariable String regNumber) {
        try {
            VehicleDTO vehicle = service.findByRegNumber(regNumber);
            return new Response(200, "OK", vehicle);
        } catch (BusinessException e) {
            return new Response(e.getHttpCode(), e.getMessage(), e.getClass().getSimpleName());
        } catch (PersistenceException e) {
            return new Response(503, "Can not access the database. Please try again later or contact your administrator", e.getMessage());
        } catch (Exception e) {
            return new Response(520, "Unknown server error. Please contact your administrator", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public Response add(@RequestBody String vehicleJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            VehicleDTO vehicle = mapper.readValue(vehicleJson, VehicleDTO.class);
            service.add(vehicle);
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
    public Response change(@RequestBody String vehicleJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            VehicleDTO vehicle = mapper.readValue(vehicleJson, VehicleDTO.class);
            service.change(vehicle);
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
