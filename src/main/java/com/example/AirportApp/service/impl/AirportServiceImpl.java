// AirportServiceImpl.java
package com.example.AirportApp.service.impl;

import com.example.AirportApp.exception.AirportAppException;
import com.example.AirportApp.model.Airport;
import com.example.AirportApp.repository.AirportRepository;
import com.example.AirportApp.service.AirportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Airport> findById(Long id) {
        if (id == null) {
            throw new AirportAppException("ID no pot ser null");
        }
        return airportRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Airport> findByIataCode(String iataCode) {
        if (iataCode == null || iataCode.length() != 3) {
            throw new AirportAppException("Codi IATA ha de tenir 3 caràcters");
        }
        return airportRepository.findByIataCode(iataCode.toUpperCase());
    }

    @Override
    @Transactional
    public Airport save(Airport airport) {
        if (airport == null) {
            throw new AirportAppException("L'aeroport no pot ser null");
        }
        if (airport.getIataCode() == null || airport.getIataCode().length() != 3) {
            throw new AirportAppException("El codi IATA és obligatori i ha de tenir 3 caràcters");
        }
        return airportRepository.save(airport);
    }

    @Override
    @Transactional
    public Airport update(Long id, Airport airportDetails) {
        return airportRepository.findById(id)
                .map(airport -> {
                    if (airportDetails.getName() != null) {
                        airport.setName(airportDetails.getName());
                    }
                    if (airportDetails.getIataCode() != null) {
                        if (airportDetails.getIataCode().length() != 3) {
                            throw new AirportAppException("El codi IATA ha de tenir 3 caràcters");
                        }
                        airport.setIataCode(airportDetails.getIataCode());
                    }
                    if (airportDetails.getCity() != null) {
                        airport.setCity(airportDetails.getCity());
                    }
                    if (airportDetails.getCountry() != null) {
                        airport.setCountry(airportDetails.getCountry());
                    }
                    return airportRepository.save(airport);
                })
                .orElseThrow(() -> new AirportAppException("Aeroport no trobat amb id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new AirportAppException("Aeroport no trobat amb id: " + id);
        }
        airportRepository.deleteById(id);
    }
}