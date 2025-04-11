package com.example.AirportApp.repository;

import com.example.AirportApp.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Override
    @NonNull
    Optional<Flight> findById(@NonNull Long id);

    Optional<Flight> findByFlightNumber(String flightNumber);

    @Query("SELECT f FROM Flight f WHERE f.departureAirport.id = :airportId OR f.arrivalAirport.id = :airportId")
    List<Flight> findByAirportId(@Param("airportId") Long airportId);

    List<Flight> findByDepartureAirportId(Long airportId);
    List<Flight> findByArrivalAirportId(Long airportId);

    @Override
    @NonNull
    List<Flight> findAll();
}