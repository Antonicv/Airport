// PlaneServiceImpl.java
package com.example.AirportApp.service.impl;

import com.example.AirportApp.exception.AirportAppException;
import com.example.AirportApp.model.Plane;
import com.example.AirportApp.repository.PlaneRepository;
import com.example.AirportApp.service.PlaneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final PlaneRepository planeRepository;

    public PlaneServiceImpl(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plane> findAll() {
        return planeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plane> findById(Long id) {
        if (id == null) {
            throw new AirportAppException("ID no pot ser null");
        }
        return planeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plane> findByRegistrationNumber(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.isEmpty()) {
            throw new AirportAppException("El número de registre no pot estar buit");
        }
        return planeRepository.findByRegistrationNumber(registrationNumber.toUpperCase());
    }

    @Override
    @Transactional
    public Plane save(Plane plane) {
        if (plane == null) {
            throw new AirportAppException("L'avió no pot ser null");
        }
        if (plane.getRegistrationNumber() == null || plane.getRegistrationNumber().isEmpty()) {
            throw new AirportAppException("El número de registre és obligatori");
        }
        if (plane.getModel() == null || plane.getModel().isEmpty()) {
            throw new AirportAppException("El model és obligatori");
        }
        if (plane.getManufacturer() == null || plane.getManufacturer().isEmpty()) {
            throw new AirportAppException("El fabricant és obligatori");
        }
        if (plane.getPassengerCapacity() <= 0) {
            throw new AirportAppException("La capacitat de passatgers ha de ser major que zero");
        }
        if (plane.getMaxRange() <= 0) {
            throw new AirportAppException("L'abast màxim ha de ser major que zero");
        }
        if (plane.getCruisingSpeed() <= 0) {
            throw new AirportAppException("La velocitat de creuer ha de ser major que zero");
        }
        return planeRepository.save(plane);
    }

    @Override
    @Transactional
    public Plane update(Long id, Plane planeDetails) {
        return planeRepository.findById(id)
                .map(plane -> {
                    if (planeDetails.getRegistrationNumber() != null && !planeDetails.getRegistrationNumber().isEmpty()) {
                        plane.setRegistrationNumber(planeDetails.getRegistrationNumber());
                    }
                    if (planeDetails.getModel() != null && !planeDetails.getModel().isEmpty()) {
                        plane.setModel(planeDetails.getModel());
                    }
                    if (planeDetails.getManufacturer() != null && !planeDetails.getManufacturer().isEmpty()) {
                        plane.setManufacturer(planeDetails.getManufacturer());
                    }
                    if (planeDetails.getPassengerCapacity() > 0) {
                        plane.setPassengerCapacity(planeDetails.getPassengerCapacity());
                    }
                    if (planeDetails.getMaxRange() > 0) {
                        plane.setMaxRange(planeDetails.getMaxRange());
                    }
                    if (planeDetails.getCruisingSpeed() > 0) {
                        plane.setCruisingSpeed(planeDetails.getCruisingSpeed());
                    }
                    return planeRepository.save(plane);
                })
                .orElseThrow(() -> new AirportAppException("Avió no trobat amb id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!planeRepository.existsById(id)) {
            throw new AirportAppException("Avió no trobat amb id: " + id);
        }
        planeRepository.deleteById(id);
    }
}