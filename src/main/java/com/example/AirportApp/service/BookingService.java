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
    List<Booking> saveAll(List<Booking> bookings);
    Booking update(Long id, Booking bookingDetails);
    List<Booking> updateAll(List<Booking> bookings);
    void deleteById(Long id);
}