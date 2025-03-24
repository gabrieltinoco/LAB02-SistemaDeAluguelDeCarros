package com.AluguelDeCarros.RentWheels.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.exemplo"})
public class RentWheelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentWheelsApplication.class, args);
	}

}
