package com.example.AirportApp.repositories;

import com.example.AirportApp.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByIataCode(String iataCode);
    Optional<Airport> findByIcaoCode(String icaoCode);
    List<Airport> findByCity(String city);
    List<Airport> findByCountry(String country);
}