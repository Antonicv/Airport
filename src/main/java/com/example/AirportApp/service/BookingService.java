package com.example.AirportApp.service;

import com.example.AirportApp.model.*;
import com.example.AirportApp.repository.BookingRepository;
import com.example.AirportApp.repository.FlightRepository;
import com.example.AirportApp.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public BookingService(BookingRepository bookingRepository,
                          FlightRepository flightRepository,
                          PassengerRepository passengerRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking save(Booking booking) {
        // Validar que el vuelo existe
        Flight flight = flightRepository.findById(booking.getFlight().getId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        // Validar que el pasajero existe
        Passenger passenger = passengerRepository.findById(booking.getPassenger().getId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        // Validar que el asiento está disponible
        if (bookingRepository.existsByFlightIdAndSeatNumber(flight.getId(), booking.getSeatNumber())) {
            throw new RuntimeException("Seat already taken");
        }

        // Validar que hay capacidad en el vuelo
        long bookingsCount = bookingRepository.countByFlightId(flight.getId());
        if (bookingsCount >= flight.getAirplane().getPassengerCapacity()) {
            throw new RuntimeException("Flight is fully booked");
        }

        return bookingRepository.save(booking);
    }

    public Booking update(Long id, Booking bookingDetails) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    booking.setSeatNumber(bookingDetails.getSeatNumber());
                    booking.setBookingReference(bookingDetails.getBookingReference());
                    return bookingRepository.save(booking);
                })
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> findByPassengerId(Long passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
    }

    public List<Booking> findByFlightId(Long flightId) {
        return bookingRepository.findByFlightId(flightId);
    }
}