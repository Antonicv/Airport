// PassengerService.java
package com.example.AirportApp.service;

import com.example.AirportApp.model.Passenger;
import java.util.List;
import java.util.Optional;

public interface PassengerService {
    List<Passenger> findAll();
    Optional<Passenger> findById(Long id);
    Optional<Passenger> findByPassportNumber(String passportNumber);
    Optional<Passenger> findByEmail(String email);
    Passenger save(Passenger passenger);
    Passenger update(Long id, Passenger passengerDetails);
    void deleteById(Long id);
    List<Passenger> saveAll(List<Passenger> passengers);
    List<Passenger> updateAll(List<Passenger> passengers);
}