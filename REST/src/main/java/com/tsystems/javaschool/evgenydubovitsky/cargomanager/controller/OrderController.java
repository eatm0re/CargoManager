package com.tsystems.javaschool.evgenydubovitsky.cargomanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.OrderDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.OrderService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.JMSProducer;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService service;
    private JMSProducer jms;

    @Autowired
    public void setService(OrderService service) {
        this.service = service;
    }

    @Autowired
    public void setJms(JMSProducer jms) {
        this.jms = jms;
    }

    @Request
    @RequestMapping({"", "/"})
    public Response getAll() throws Exception {
        List<OrderDTO> orders = service.getAll();
        return new Response(200, "OK", orders);
    }

    @Request
    @RequestMapping("/{id}")
    public Response findById(@PathVariable long id) throws Exception {
        OrderDTO order = service.findById(id);
        return new Response(200, "OK", order);
    }

    @Request
    @RequestMapping("/{id}/capacity")
    public Response capacityNeeded(@PathVariable long id) throws Exception {
        long res = service.capacityNeeded(id);
        return new Response(200, "OK", res);
    }

    @Request
    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public Response add(@RequestBody String orderJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OrderDTO order = mapper.readValue(orderJson, OrderDTO.class);
        long id = service.add(order);
        jms.sendMessage();
        return new Response(200, "OK", id);
    }

    @Request
    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
    public Response progressReport(@PathVariable long id) throws Exception {
        service.progressReport(id);
        jms.sendMessage();
        return Response.OK;
    }

    @Request
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Response interrupt(@PathVariable long id) throws Exception {
        service.interrupt(id);
        jms.sendMessage();
        return Response.OK;
    }
}
