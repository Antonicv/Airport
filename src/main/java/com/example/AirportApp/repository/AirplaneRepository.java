package com.example.AirportApp.repository;

import com.example.AirportApp.model.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    Airplane findByRegistrationNumber(String registrationNumber);
    List<Airplane> findByModel(String model);
    List<Airplane> findByPassengerCapacityGreaterThan(int capacity);
}