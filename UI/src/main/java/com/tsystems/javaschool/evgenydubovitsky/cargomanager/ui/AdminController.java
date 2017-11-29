package com.tsystems.javaschool.evgenydubovitsky.cargomanager.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping({"", "/"})
    public String index() {
        return "admin";
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

    @RequestMapping(value = "driver/edit", method = RequestMethod.POST)
    public ModelAndView changeConcreteDriver(
            @RequestParam(value = "persNumber", required = false) String persNumber) {
        ModelAndView model = new ModelAndView("driver/change");
        if (persNumber != null) {
            model.addObject("persNumber", persNumber);
        }
        return model;
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

    @RequestMapping(value = "vehicle/edit", method = RequestMethod.POST)
    public ModelAndView changeConcreteVehicle(
            @RequestParam(value = "regNumber", required = false) String regNumber) {
        ModelAndView model = new ModelAndView("vehicle/change");
        if (regNumber != null) {
            model.addObject("regNumber", regNumber);
        }
        return model;
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
