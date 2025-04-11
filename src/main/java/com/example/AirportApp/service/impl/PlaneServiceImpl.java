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
    public List<Plane> saveAll(List<Plane> planes) {
        if (planes == null || planes.isEmpty()) {
            throw new AirportAppException("La lista de aviones no puede ser nula o vacía");
        }
        for (Plane plane : planes) {
            if (plane.getRegistrationNumber() == null || plane.getRegistrationNumber().isEmpty()) {
                throw new AirportAppException("El número de registro es obligatorio para cada avión");
            }
            if (plane.getModel() == null || plane.getModel().isEmpty()) {
                throw new AirportAppException("El modelo es obligatorio para cada avión");
            }
            if (plane.getManufacturer() == null || plane.getManufacturer().isEmpty()) {
                throw new AirportAppException("El fabricante es obligatorio para cada avión");
            }
            if (plane.getPassengerCapacity() <= 0) {
                throw new AirportAppException("La capacidad de pasajeros debe ser mayor que cero");
            }
            if (plane.getMaxRange() <= 0) {
                throw new AirportAppException("El alcance máximo debe ser mayor que cero");
            }
            if (plane.getCruisingSpeed() <= 0) {
                throw new AirportAppException("La velocidad de crucero debe ser mayor que cero");
            }
        }
        return planeRepository.saveAll(planes);
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
                   
                    return planeRepository.save(plane);
                })
                .orElseThrow(() -> new AirportAppException("Avió no trobat amb id: " + id));
    }

    @Override
    @Transactional
    public List<Plane> updateAll(List<Plane> planes) {
        if (planes == null || planes.isEmpty()) {
            throw new AirportAppException("La lista de aviones no puede ser nula o vacía");
        }
        for (Plane plane : planes) {
            if (plane.getId() == null || !planeRepository.existsById(plane.getId())) {
                throw new AirportAppException("No se encontró un avión con el ID proporcionado: " + plane.getId());
            }
            if (plane.getRegistrationNumber() != null && plane.getRegistrationNumber().isEmpty()) {
                throw new AirportAppException("El número de registro no puede estar vacío");
            }
            if (plane.getModel() != null && plane.getModel().isEmpty()) {
                throw new AirportAppException("El modelo no puede estar vacío");
            }
            if (plane.getManufacturer() != null && plane.getManufacturer().isEmpty()) {
                throw new AirportAppException("El fabricante no puede estar vacío");
            }
           
        }
        return planeRepository.saveAll(planes);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            throw new AirportAppException("L'ID no pot ser null");
        }
        if (!planeRepository.existsById(id)) {
            throw new AirportAppException("Avió no trobat amb id: " + id);
        }
        planeRepository.deleteById(id);
    }
}