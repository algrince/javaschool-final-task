package com.algrince.finaltask.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    @GetMapping("/home")
    public String showHomepage() {
        return "home/homepage";
    }
}
