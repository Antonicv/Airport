package com.example.AirportApp.service;

import com.example.AirportApp.model.Flight;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> findAll();
    Optional<Flight> findById(Long id);
    Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findByAirportId(Long airportId);
    Flight save(Flight flight);
    Flight update(Long id, Flight flightDetails);
    void deleteById(Long id);
}