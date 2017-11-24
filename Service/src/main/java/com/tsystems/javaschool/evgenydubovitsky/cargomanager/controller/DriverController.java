package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DriverDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/all")
    public List<DriverDTO> getAll() {
        return service.getAll();
    }
}
