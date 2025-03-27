package com.example.AirportApp.service.impl;

import com.example.AirportApp.model.Plane;
import com.example.AirportApp.repositories.PlaneRepository;
import com.example.AirportApp.service.PlaneService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final PlaneRepository planeRepository;

    public PlaneServiceImpl(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @Override
    public List<Plane> findAll() {
        return planeRepository.findAll();
    }

    @Override
    public Optional<Plane> findById(Long id) {
        return planeRepository.findById(id);
    }

    @Override
    public Optional<Plane> findByRegistrationNumber(String registrationNumber) {
        return planeRepository.findByRegistrationNumber(registrationNumber);
    }

    @Override
    public Plane save(Plane plane) {
        return planeRepository.save(plane);
    }

    @Override
    public Plane update(Long id, Plane planeDetails) {
        return planeRepository.findById(id)
                .map(plane -> {
                    plane.setModel(planeDetails.getModel());
                    plane.setRegistrationNumber(planeDetails.getRegistrationNumber());
                    // Actualizar otros campos
                    return planeRepository.save(plane);
                })
                .orElseThrow(() -> new RuntimeException("Plane not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        planeRepository.deleteById(id);
    }
}