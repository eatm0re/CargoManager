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

    @RequestMapping("town")
    public String showAllCities() {
        return "town/all";
    }

    @RequestMapping("town/add")
    public String addTown() {
        return "town/add";
    }

    @RequestMapping("order")
    public String showAllOrders() {
        return "order/all";
    }

    @RequestMapping("order/details")
    public String showOrderDetails() {
        return "order/details";
    }

    @RequestMapping("order/add")
    public String addOrder() {
        return "order/add";
    }

    @RequestMapping("cargo")
    public String showAllCargoes() {
        return "cargo/all";
    }

    @RequestMapping("cargo/search")
    public String searchForCargoes() {
        return "cargo/search";
    }

    @RequestMapping("cargo/add")
    public String addCargo() {
        return "cargo/add";
    }
}
