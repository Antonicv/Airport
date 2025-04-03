package com.example.AirportApp.model;


import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "airports",
        uniqueConstraints = @UniqueConstraint(columnNames = {"code", "name"}))
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código IATA es obligatorio")
    @Size(min = 3, max = 3, message = "El código IATA debe tener exactamente 3 caracteres")
    @Column(nullable = false, unique = true, length = 3)
    private String code; // Código IATA (ej: BCN, MAD)

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "La ciudad es obligatoria")
    @Column(nullable = false, length = 50)
    private String city;

    @NotBlank(message = "El país es obligatorio")
    @Column(nullable = false, length = 50)
    private String country;

    // Relación con Vuelos de Salida
    @OneToMany(mappedBy = "departureAirport",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            orphanRemoval = true)
    private List<Flight> departingFlights = new ArrayList<>();

    // Relación con Vuelos de Llegada
    @OneToMany(mappedBy = "arrivalAirport",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            orphanRemoval = true)
    private List<Flight> arrivingFlights = new ArrayList<>();

    // Constructores
    public Airport() {}

    public Airport(String code, String name, String city, String country) {
        this.code = code.toUpperCase(); // Aseguramos mayúsculas para códigos IATA
        this.name = name;
        this.city = city;
        this.country = country;
    }

    // Métodos de gestión de relaciones
    public void addDepartingFlight(Flight flight) {
        departingFlights.add(flight);
        flight.setDepartureAirport(this);
    }

    public void removeDepartingFlight(Flight flight) {
        departingFlights.remove(flight);
        flight.setDepartureAirport(null);
    }

    public void addArrivingFlight(Flight flight) {
        arrivingFlights.add(flight);
        flight.setArrivalAirport(this);
    }

    public void removeArrivingFlight(Flight flight) {
        arrivingFlights.remove(flight);
        flight.setArrivalAirport(null);
    }

    // Getters i Setters
    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Flight> getDepartingFlights() {
        return departingFlights;
    }

    public List<Flight> getArrivingFlights() {
        return arrivingFlights;
    }

    // Metode toString()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;
        return id != null && id.equals(((Airport) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}