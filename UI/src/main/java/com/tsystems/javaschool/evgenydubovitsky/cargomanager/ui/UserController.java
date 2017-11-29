package com.tsystems.javaschool.evgenydubovitsky.cargomanager.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping({"", "/"})
    public String index() {
        return "user";
    }

    @RequestMapping("/order")
    public String order() {
        return "user/order";
    }
}
