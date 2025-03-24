package com.AluguelDeCarros.RentWheels.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AluguelDeCarros.RentWheels.Services.Service;

@RestController
public class Controller {

    Service service = new Service();

    @GetMapping("/test")
    public String hello(){
        return service.hello();
    }

    @GetMapping("/exemplo")
    public String exemplo(){
        return service.exemplo();
    }

}
