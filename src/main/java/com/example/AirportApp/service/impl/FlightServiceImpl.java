package com.example.AirportApp.service.impl;

import com.example.AirportApp.model.Flight;
import com.example.AirportApp.repository.FlightRepository;
import com.example.AirportApp.service.FlightService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByAirportId(Long airportId) {
        return flightRepository.findByDepartureAirportId(airportId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Flight> findByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByDepartureAirportId(Long airportId) {
        return flightRepository.findByDepartureAirportId(airportId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> findByArrivalAirportId(Long airportId) {
        return flightRepository.findByArrivalAirportId(airportId);
    }

    @Override
    @Transactional
    public Flight save(Flight flight) {
        validateFlight(flight);
        return flightRepository.save(flight);
    }

    @Override
    @Transactional
    public Flight update(Long id, Flight flightDetails) {
        return flightRepository.findById(id)
                .map(existingFlight -> {
                    existingFlight.setFlightNumber(flightDetails.getFlightNumber());
                    existingFlight.setDepartureTime(flightDetails.getDepartureTime());
                    existingFlight.setArrivalTime(flightDetails.getArrivalTime());
                    existingFlight.setDepartureAirport(flightDetails.getDepartureAirport());
                    existingFlight.setArrivalAirport(flightDetails.getArrivalAirport());
                    existingFlight.setPlane(flightDetails.getPlane());
                    existingFlight.setTicketPrice(flightDetails.getTicketPrice());
                    existingFlight.setAvailableSeats(flightDetails.getAvailableSeats());
                    validateFlight(existingFlight);
                    return flightRepository.save(existingFlight);
                })
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Flight> saveAll(List<Flight> flights) {
        flights.forEach(this::validateFlight);
        return flightRepository.saveAll(flights);
    }

    @Override
    @Transactional
    public List<Flight> updateAll(List<Flight> flights) {
        return flights.stream()
                .map(flight -> {
                    if (flight.getId() != null) {
                        return update(flight.getId(), flight);
                    }
                    return save(flight);
                })
                .toList();
    }

    private void validateFlight(Flight flight) {
        if (flight.getFlightNumber() == null || flight.getFlightNumber().isBlank()) {
            throw new IllegalArgumentException("Flight number cannot be empty");
        }
        
        if (flight.getDepartureTime() == null) {
            throw new IllegalArgumentException("Departure time cannot be null");
        }
        
        if (flight.getArrivalTime() == null) {
            throw new IllegalArgumentException("Arrival time cannot be null");
        }
        
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())) {
            throw new IllegalArgumentException("Arrival time must be after departure time");
        }
        
        if (flight.getDepartureAirport() == null) {
            throw new IllegalArgumentException("Departure airport cannot be null");
        }
        
        if (flight.getArrivalAirport() == null) {
            throw new IllegalArgumentException("Arrival airport cannot be null");
        }
        
        if (flight.getPlane() == null) {
            throw new IllegalArgumentException("Plane cannot be null");
        }
        
        if (flight.getAvailableSeats() < 0) {
            throw new IllegalArgumentException("Available seats cannot be negative");
        }
        
        if (flight.getTicketPrice() <= 0) {
            throw new IllegalArgumentException("Ticket price must be positive");
        }
    }
}