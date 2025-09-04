package com.ppu.artifact_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Welcome to your Spring Boot Application!";
    }

    @GetMapping("/about")
    public String about() {
        return "Welcome to my about page";
    }
}
