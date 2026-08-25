package com.example.jobscheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping(value = {"/", "/login", "/dashboard", "/queues", "/jobs", "/workers", "/dlq"})
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/_redirect")
    public String redirectToFrontend() {
        return "redirect:/";
    }
}
