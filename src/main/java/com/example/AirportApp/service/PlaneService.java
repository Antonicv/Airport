// PlaneService.java
package com.example.AirportApp.service;

import com.example.AirportApp.model.Plane;
import java.util.List;
import java.util.Optional;

public interface PlaneService {
    List<Plane> findAll();
    Optional<Plane> findById(Long id);
    Optional<Plane> findByRegistrationNumber(String registrationNumber);
    Plane save(Plane plane);
    Plane update(Long id, Plane planeDetails);
    void deleteById(Long id);
}