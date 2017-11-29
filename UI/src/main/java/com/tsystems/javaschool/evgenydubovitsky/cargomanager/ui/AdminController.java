package com.tsystems.javaschool.evgenydubovitsky.cargomanager.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping({"", "/"})
    public String index() {
        return "index";
    }

    @RequestMapping("driver")
    public String showAllDrivers() {
        return "driver/list";
    }

    @RequestMapping("driver/add")
    public String addDriver() {
        return "driver/add";
    }

    @RequestMapping("driver/edit")
    public String changeDriver() {
        return "driver/change";
    }

    @RequestMapping("vehicle")
    public String showAllVehicles() {
        return "vehicle/list";
    }

    @RequestMapping("vehicle/add")
    public String addVehicle() {
        return "vehicle/add";
    }

    @RequestMapping("vehicle/edit")
    public String changeVehicle() {
        return "vehicle/change";
    }

    @RequestMapping("city")
    public String showAllCities() {
        return "city/list";
    }

    @RequestMapping("city/add")
    public String addTown() {
        return "city/add";
    }

    @RequestMapping("order")
    public String showAllOrders() {
        return "order/list";
    }

    @RequestMapping("order/add")
    public String addOrder() {
        return "order/add";
    }

    @RequestMapping("cargo")
    public String showAllCargoes() {
        return "cargo/list";
    }

    @RequestMapping("cargo/add")
    public String addCargo() {
        return "cargo/add";
    }
}
