// BookingService.java
package com.example.AirportApp.service;

import com.example.AirportApp.model.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> findAll();
    Optional<Booking> findById(Long id);
    Optional<Booking> findByBookingReference(String bookingReference);
    List<Booking> findByPassengerId(Long passengerId);
    List<Booking> findByFlightId(Long flightId);
    Booking save(Booking booking);
    Booking update(Long id, Booking bookingDetails);
    void deleteById(Long id);
}