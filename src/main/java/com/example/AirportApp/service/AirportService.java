package com.example.AirportApp.service;

import com.example.AirportApp.model.Airport;
import com.example.AirportApp.model.Flight;
import com.example.AirportApp.repository.AirportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    public Optional<Airport> findById(Long id) {
        return airportRepository.findById(id);
    }

    public Airport save(Airport airport) {
        return airportRepository.save(airport);
    }

    public Airport update(Long id, Airport airportDetails) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airport.setName(airportDetails.getName());
                    airport.setCode(airportDetails.getCode());
                    airport.setCity(airportDetails.getCity());
                    airport.setCountry(airportDetails.getCountry());
                    return airportRepository.save(airport);
                })
                .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));
    }

    public void delete(Long id) {
        airportRepository.deleteById(id);
    }

    public List<Flight> findDepartingFlights(Long airportId) {
        return airportRepository.findByDepartureAirportId(airportId);
    }

    public List<Flight> findArrivingFlights(Long airportId) {
        return airportRepository.findByArrivalAirportId(airportId);
    }
}