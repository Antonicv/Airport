package com.example.AirportApp.service.impl;

import com.example.AirportApp.model.Flight;
import com.example.AirportApp.repositories.FlightRepository;
import com.example.AirportApp.service.FlightService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    public Optional<Flight> findByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    @Override
    public List<Flight> findByAirportId(Long airportId) {
        return flightRepository.findByAirportId(airportId);
    }

    @Override
    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight update(Long id, Flight flightDetails) {
        return flightRepository.findById(id)
                .map(flight -> {
                    flight.setFlightNumber(flightDetails.getFlightNumber());
                    flight.setDepartureTime(flightDetails.getDepartureTime());
                    // Actualiza otros campos según necesidad
                    return flightRepository.save(flight);
                })
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }
}