package com.lab02.rentwheels;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/test")
    public String hello() {
        return "Hello World!";
    }
}
