package com.example.AirportApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.AirportApp")
public class AirportApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirportApplication.class, args);
    }
}


