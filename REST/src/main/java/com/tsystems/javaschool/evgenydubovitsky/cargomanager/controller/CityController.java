package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CityDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.CityService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    private CityService service;

    @Autowired
    public void setService(CityService service) {
        this.service = service;
    }

    @Request
    @RequestMapping(path = {"", "/"})
    public Response getAll() throws Exception {
        List<CityDTO> cities = service.getAll();
        return new Response(200, "OK", cities);
    }

    @Request
    @RequestMapping("/{name}")
    public Response findByName(@PathVariable String name) throws Exception {
        CityDTO city = service.findByName(name);
        return new Response(200, "OK", city);
    }

    @Request
    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public Response add(@RequestBody String cityJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CityDTO city = mapper.readValue(cityJson, CityDTO.class);
        service.add(city);
        return new Response(200, "OK", null);
    }
}
