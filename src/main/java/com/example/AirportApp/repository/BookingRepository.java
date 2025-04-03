package com.example.AirportApp.repository;

import com.example.AirportApp.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Reservas por pasajero
    List<Booking> findByPassengerId(Long passengerId);

    // Reservas por vuelo
    List<Booking> findByFlightId(Long flightId);

    // Verificar si un asiento está ocupado en un vuelo
    boolean existsByFlightIdAndSeatNumber(Long flightId, String seatNumber);
}