package com.example.AirportApp.repositories;

import com.example.AirportApp.model.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
    Optional<Plane> findByRegistrationNumber(String registrationNumber);
    List<Plane> findByModel(String model);
    List<Plane> findByManufacturer(String manufacturer);
    List<Plane> findByPassengerCapacityGreaterThanEqual(int minCapacity);
}