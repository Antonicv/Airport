package com.example.AirportApp.service;

import com.example.AirportApp.model.*;
import com.example.AirportApp.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirplaneService airplaneService;
    private final AirportService airportService;

    public FlightService(FlightRepository flightRepository,
                         AirplaneService airplaneService,
                         AirportService airportService) {
        this.flightRepository = flightRepository;
        this.airplaneService = airplaneService;
        this.airportService = airportService;
    }

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    public Flight save(Flight flight) {
        // Validar que el avión existe
        airplaneService.findById(flight.getAirplane().getId())
                .orElseThrow(() -> new RuntimeException("Airplane not found"));

        // Validar que los aeropuertos existen
        airportService.findById(flight.getDepartureAirport().getId())
                .orElseThrow(() -> new RuntimeException("Departure airport not found"));
        airportService.findById(flight.getArrivalAirport().getId())
                .orElseThrow(() -> new RuntimeException("Arrival airport not found"));

        return flightRepository.save(flight);
    }

    public Flight update(Long id, Flight flightDetails) {
        return flightRepository.findById(id)
                .map(flight -> {
                    flight.setFlightNumber(flightDetails.getFlightNumber());
                    flight.setDepartureTime(flightDetails.getDepartureTime());
                    flight.setArrivalTime(flightDetails.getArrivalTime());
                    flight.setAirplane(flightDetails.getAirplane());
                    flight.setDepartureAirport(flightDetails.getDepartureAirport());
                    flight.setArrivalAirport(flightDetails.getArrivalAirport());
                    return flightRepository.save(flight);
                })
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
    }

    public void delete(Long id) {
        flightRepository.deleteById(id);
    }

    public List<Booking> findBookingsByFlightId(Long flightId) {
        return flightRepository.findBookingsByFlightId(flightId);
    }

    public List<CrewMember> findCrewByFlightId(Long flightId) {
        return flightRepository.findCrewMembersByFlightId(flightId);
    }

    public List<Flight> findAvailableFlights() {
        return flightRepository.findAvailableFlights();
    }

    public boolean isSeatAvailable(Long flightId, String seatNumber) {
        return !flightRepository.existsBookingForSeat(flightId, seatNumber);
    }
}