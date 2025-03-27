package com.example.AirportApp.service.impl;

import com.example.AirportApp.model.Passenger;
import com.example.AirportApp.repositories.PassengerRepository;
import com.example.AirportApp.service.PassengerService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Optional<Passenger> findById(Long id) {
        return passengerRepository.findById(id);
    }

    @Override
    public Optional<Passenger> findByPassportNumber(String passportNumber) {
        return passengerRepository.findByPassportNumber(passportNumber);
    }

    @Override
    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public Passenger update(Long id, Passenger passengerDetails) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passenger.setFirstName(passengerDetails.getFirstName());
                    passenger.setLastName(passengerDetails.getLastName());
                    // Actualizar otros campos
                    return passengerRepository.save(passenger);
                })
                .orElseThrow(() -> new RuntimeException("Passenger not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        passengerRepository.deleteById(id);
    }
}