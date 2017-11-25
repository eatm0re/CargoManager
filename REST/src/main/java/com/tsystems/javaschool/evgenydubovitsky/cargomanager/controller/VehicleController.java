package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.VehicleDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Response findByPersNumber(@PathVariable String regNumber) {
        try {
            VehicleDTO driver = service.findByRegNumber(regNumber);
            return new Response(200, "OK", driver);
        } catch (BusinessException e) {
            return new Response(e.getHttpCode(), e.getMessage(), e.getClass().getSimpleName());
        } catch (PersistenceException e) {
            return new Response(503, "Can not access the database. Please try again later or contact your administrator", e.getMessage());
        } catch (Exception e) {
            return new Response(520, "Unknown server error. Please contact your administrator", e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
