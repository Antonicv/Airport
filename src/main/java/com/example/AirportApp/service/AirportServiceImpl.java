package com.example.AirportApp.service.impl;

import com.example.AirportApp.model.Airport;
import com.example.AirportApp.repositories.AirportRepository;
import com.example.AirportApp.service.AirportService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Optional<Airport> findById(Long id) {
        return airportRepository.findById(id);
    }

    @Override
    public Optional<Airport> findByIataCode(String iataCode) {
        return airportRepository.findByIataCode(iataCode);
    }

    @Override
    public Airport save(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public Airport update(Long id, Airport airportDetails) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airport.setName(airportDetails.getName());
                    airport.setIataCode(airportDetails.getIataCode());
                    airport.setCity(airportDetails.getCity());

                    return airportRepository.save(airport);
                })
                .orElseThrow(() -> new RuntimeException("AirportApp not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        airportRepository.deleteById(id);
    }
}