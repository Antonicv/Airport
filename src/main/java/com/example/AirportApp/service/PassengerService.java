package com.example.AirportApp.service;

import com.example.AirportApp.model.Booking;
import com.example.AirportApp.model.Passenger;
import com.example.AirportApp.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> findById(Long id) {
        return passengerRepository.findById(id);
    }

    public Passenger save(Passenger passenger) {
        if (passengerRepository.existsByPassportNumber(passenger.getPassportNumber())) {
            throw new RuntimeException("Passport number already exists");
        }
        if (passengerRepository.existsByEmail(passenger.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return passengerRepository.save(passenger);
    }

    public Passenger update(Long id, Passenger passengerDetails) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passenger.setFirstName(passengerDetails.getFirstName());
                    passenger.setLastName(passengerDetails.getLastName());
                    passenger.setEmail(passengerDetails.getEmail());
                    return passengerRepository.save(passenger);
                })
                .orElseThrow(() -> new RuntimeException("Passenger not found with id: " + id));
    }

    public void delete(Long id) {
        passengerRepository.deleteById(id);
    }

    public List<Booking> findBookingsByPassengerId(Long passengerId) {
        return passengerRepository.findBookingsByPassengerId(passengerId);
    }
}