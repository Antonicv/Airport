package com.example.AirportApp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "airplanes")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(nullable = false, unique = true, length = 20)
    private String registrationNumber;

    @Column(nullable = false)
    private int passengerCapacity;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flights = new ArrayList<>();

    // Constructores
    public Airplane() {}

    public Airplane(String model, String registrationNumber, int passengerCapacity) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.passengerCapacity = passengerCapacity;
    }

    // Métodos de relación bidireccional
    public void addFlight(Flight flight) {
        flights.add(flight);
        flight.setAirplane(this);
    }

    public void removeFlight(Flight flight) {
        flights.remove(flight);
        flight.setAirplane(null);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", passengerCapacity=" + passengerCapacity +
                '}';
    }
}