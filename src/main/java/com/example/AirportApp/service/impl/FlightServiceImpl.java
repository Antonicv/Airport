package com.example.AirportApp.service.impl;

import com.example.AirportApp.exception.AirportAppException;
import com.example.AirportApp.model.Flight;
import com.example.AirportApp.repository.FlightRepository;
import com.example.AirportApp.service.FlightService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service // Aquesta anotació és crucial
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Flight> findById(Long id) {
        if (id == null) {
            throw new AirportAppException("ID no pot ser null");
        }
        return flightRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Flight> findByFlightNumber(String flightNumber) {
        if (flightNumber == null || flightNumber.isEmpty()) {
            throw new AirportAppException("Número de vol no pot estar buit");
        }
        return flightRepository.findByFlightNumber(flightNumber.toUpperCase());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByDepartureAirportId(Long airportId) {
        if (airportId == null) {
            throw new AirportAppException("ID d'aeroport no pot ser null");
        }
        return flightRepository.findByDepartureAirportId(airportId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByArrivalAirportId(Long airportId) {
        if (airportId == null) {
            throw new AirportAppException("ID d'aeroport no pot ser null");
        }
        return flightRepository.findByArrivalAirportId(airportId);
    }

    @Override
    @Transactional
    public Flight save(Flight flight) {
        if (flight == null) {
            throw new AirportAppException("El vol no pot ser null");
        }
        if (flight.getFlightNumber() == null || flight.getFlightNumber().isEmpty()) {
            throw new AirportAppException("El número de vol és obligatori");
        }
        if (flight.getDepartureTime() == null || flight.getArrivalTime() == null) {
            throw new AirportAppException("Les hores de sortida i arribada són obligatòries");
        }
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())) {
            throw new AirportAppException("L'hora de sortida no pot ser posterior a l'hora d'arribada");
        }
        if (flight.getDepartureAirport() == null || flight.getArrivalAirport() == null) {
            throw new AirportAppException("Els aeroports de sortida i arribada són obligatoris");
        }
        if (flight.getDepartureAirport().equals(flight.getArrivalAirport())) {
            throw new AirportAppException("L'aeroport de sortida i arribada no poden ser el mateix");
        }
        return flightRepository.save(flight);
    }

    @Override
    @Transactional
    public Flight update(Long id, Flight flightDetails) {
        return flightRepository.findById(id)
                .map(flight -> {
                    if (flightDetails.getFlightNumber() != null) {
                        flight.setFlightNumber(flightDetails.getFlightNumber());
                    }
                    if (flightDetails.getDepartureTime() != null) {
                        flight.setDepartureTime(flightDetails.getDepartureTime());
                    }
                    if (flightDetails.getArrivalTime() != null) {
                        flight.setArrivalTime(flightDetails.getArrivalTime());
                    }
                    if (flightDetails.getDepartureAirport() != null) {
                        flight.setDepartureAirport(flightDetails.getDepartureAirport());
                    }
                    if (flightDetails.getArrivalAirport() != null) {
                        flight.setArrivalAirport(flightDetails.getArrivalAirport());
                    }
                    if (flightDetails.getPlane() != null) {
                        flight.setPlane(flightDetails.getPlane());
                    }
                    if (flightDetails.getTicketPrice() > 0) {
                        flight.setTicketPrice(flightDetails.getTicketPrice());
                    }
                    if (flightDetails.getAvailableSeats() >= 0) {
                        flight.setAvailableSeats(flightDetails.getAvailableSeats());
                    }
                    return flightRepository.save(flight);
                })
                .orElseThrow(() -> new AirportAppException("Vol no trobat amb id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new AirportAppException("Vol no trobat amb id: " + id);
        }
        flightRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByAirportId(Long airportId) {
        if (airportId == null) {
            throw new AirportAppException("ID d'aeroport no pot ser null");
        }

        List<Flight> departures = flightRepository.findByDepartureAirportId(airportId);
        List<Flight> arrivals = flightRepository.findByArrivalAirportId(airportId);

        Set<Flight> combinedFlights = new HashSet<>(departures);
        combinedFlights.addAll(arrivals);

        return new ArrayList<>(combinedFlights);
    }
}
