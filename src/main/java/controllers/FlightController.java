package com.example.Airport.controller;

import com.example.Airport.model.Airport;
import com.example.Airport.repository.AirportRepository;
import com.example.Airport.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    @Autowired
    AirportService airportService;

    @GetMapping
    public List<Airport> getAllAirports() {

        return airportService.getAllAirports();
    }

    @GetMapping("/{id}")
    public Airport getAirportById(@PathVariable String id){
        return airportService.getAirportById(id).orElse(null);
    }

    @PostMapping
    public Airport createAirport(@RequestBody Airport airport){
        return airportService.createAirport(airport);
    }

    @DeleteMapping
    public String deleteAllAirports(){
        airportService.deleteAllAirports();
        return "All airports deleted";
    }
}