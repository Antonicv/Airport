package com.example.AirportApp.repositories;

import com.example.AirportApp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingReference(String bookingReference);
    List<Booking> findByPassengerId(Long passengerId);
    List<Booking> findByFlightId(Long flightId);

    @Query("SELECT b FROM Booking b WHERE b.flight.id = ?1 AND b.status = 'CONFIRMED'")
    List<Booking> findConfirmedBookingsByFlightId(Long flightId);

    long countByFlightIdAndStatus(Long flightId, String status);
}