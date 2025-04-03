package com.example.AirportApp.repository;

import com.example.AirportApp.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    // Ejemplo de método personalizado
    boolean existsByPassportNumber(String passportNumber);

    // Ejemplo de consulta derivada del nombre del método
    Passenger findByEmail(String email);
}