package com.example.AirportApp.controllers;

import com.example.AirportApp.model.Flight;
import com.example.AirportApp.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    private final FlightService flightService; // Nota: Es demana la interfície

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return flightService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-number/{flightNumber}")
    public ResponseEntity<Flight> getFlightByNumber(@PathVariable String flightNumber) {
        return flightService.findByFlightNumber(flightNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-airport/{airportId}")
    public ResponseEntity<List<Flight>> getFlightsByAirport(@PathVariable Long airportId) {
        return ResponseEntity.ok(flightService.findByAirportId(airportId));
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.save(flight));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Flight>> createFlightsBatch(@RequestBody List<Flight> flights) {
        return ResponseEntity.ok(flightService.saveAll(flights));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flightDetails) {
        return ResponseEntity.ok(flightService.update(id, flightDetails));
    }

    @PutMapping("/batch")
    public ResponseEntity<List<Flight>> updateFlightsBatch(@RequestBody List<Flight> flights) {
        return ResponseEntity.ok(flightService.updateAll(flights));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        if (flightService.findById(id).isPresent()) {
            flightService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}