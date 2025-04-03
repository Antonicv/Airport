package com.example.AirportApp.service;

import com.example.AirportApp.model.Airplane;
import com.example.AirportApp.model.Flight;
import com.example.AirportApp.repository.AirplaneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;

    public AirplaneService(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    public Optional<Airplane> findById(Long id) {
        return airplaneRepository.findById(id);
    }

    public Airplane save(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    public Airplane update(Long id, Airplane airplaneDetails) {
        return airplaneRepository.findById(id)
                .map(airplane -> {
                    airplane.setModel(airplaneDetails.getModel());
                    airplane.setRegistrationNumber(airplaneDetails.getRegistrationNumber());
                    airplane.setPassengerCapacity(airplaneDetails.getPassengerCapacity());
                    return airplaneRepository.save(airplane);
                })
                .orElseThrow(() -> new RuntimeException("Airplane not found with id: " + id));
    }

    public void delete(Long id) {
        airplaneRepository.deleteById(id);
    }

    public List<Flight> findFlightsByAirplaneId(Long airplaneId) {
        return airplaneRepository.findByAirplaneId(airplaneId);
    }

    public boolean isRegistrationNumberUnique(String registrationNumber) {
        return !airplaneRepository.existsByRegistrationNumber(registrationNumber);
    }
}