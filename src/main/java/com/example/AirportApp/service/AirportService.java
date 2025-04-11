// AirportService.java
package com.example.AirportApp.service;

import com.example.AirportApp.model.Airport;
import java.util.List;
import java.util.Optional;

public interface AirportService {
    List<Airport> findAll();
    Optional<Airport> findById(Long id);
    Optional<Airport> findByIataCode(String iataCode);
    Airport save(Airport airport);
    List<Airport> saveAll(List<Airport> airports);
    Airport update(Long id, Airport airportDetails);
    List<Airport> updateAll(List<Airport> airports);
    void deleteById(Long id);
}