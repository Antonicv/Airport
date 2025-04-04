
package com.example.AirportApp.repository;

import com.example.AirportApp.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByPassportNumber(String passportNumber);
    Optional<Passenger> findByEmail(String email);
    List<Passenger> findByLastName(String lastName);
    List<Passenger> findByNationality(String nationality);
}