package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CargoDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.CargoService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargo")
public class CargoController {

    private CargoService service;

    @Autowired
    public void setService(CargoService service) {
        this.service = service;
    }

    @Request
    @RequestMapping({"", "/"})
    public Response getAll() throws Exception {
        List<CargoDTO> cargoes = service.getAll();
        return new Response(200, "OK", cargoes);
    }

    @Request
    @RequestMapping("/{id}")
    public Response findById(@PathVariable long id) throws Exception {
        CargoDTO cargo = service.findById(id);
        return new Response(200, "OK", cargo);
    }

    @Request
    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public Response add(@RequestBody String cargoJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CargoDTO cargo = mapper.readValue(cargoJson, CargoDTO.class);
        long id = service.add(cargo);
        return new Response(200, "OK", id);
    }
}
