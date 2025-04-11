package com.example.AirportApp.controllers;

import com.example.AirportApp.model.Passenger;
import com.example.AirportApp.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        return passengerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-passport/{passport}")
    public ResponseEntity<Passenger> getPassengerByPassport(@PathVariable String passport) {
        return passengerService.findByPassportNumber(passport)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
        return ResponseEntity.ok(passengerService.save(passenger));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Passenger>> createPassengersBatch(@RequestBody List<Passenger> passengers) {
        return ResponseEntity.ok(passengerService.saveAll(passengers));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger passengerDetails) {
        return ResponseEntity.ok(passengerService.update(id, passengerDetails));
    }

    @PutMapping("/batch")
    public ResponseEntity<List<Passenger>> updatePassengersBatch(@RequestBody List<Passenger> passengers) {
        return ResponseEntity.ok(passengerService.updateAll(passengers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}