package com.example.AirportApp.controllers;

import com.example.AirportApp.model.Airport;
import com.example.AirportApp.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        return ResponseEntity.ok(airportService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        return airportService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) {
        return ResponseEntity.ok(airportService.save(airport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport airportDetails) {
        return ResponseEntity.ok(airportService.update(id, airportDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<Airport> getAirportByCode(@PathVariable String code) {
        return airportService.findByIataCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/batch")
public ResponseEntity<List<Airport>> createAirportsBatch(@RequestBody List<Airport> airports) {
    return ResponseEntity.ok(airportService.saveAll(airports));
}

@PutMapping("/batch")
public ResponseEntity<List<Airport>> updateAirportsBatch(@RequestBody List<Airport> airports) {
    return ResponseEntity.ok(airportService.updateAll(airports));
}
}