package com.example.AirportApp.repository;

import com.example.AirportApp.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Vuelos por aeropuerto de salida
    List<Flight> findByDepartureAirportId(Long airportId);

    // Vuelos por aeropuerto de llegada
    List<Flight> findByArrivalAirportId(Long airportId);

    // Vuelos por avión
    List<Flight> findByAirplaneId(Long airplaneId);

    // Vuelos con capacidad disponible
    @Query("SELECT f FROM Flight f WHERE f.passengerCapacity > (SELECT COUNT(b) FROM Booking b WHERE b.flight = f)")
    List<Flight> findAvailableFlights();
}