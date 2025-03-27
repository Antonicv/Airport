package com.example.AirportApp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 3)
    private String iataCode; // 3-letter IATA code (e.g., "JFK")

    @Column(unique = true, length = 4)
    private String icaoCode; // 4-letter ICAO code (e.g., "KJFK")

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column
    private int altitude; // in feet

    @Column
    private String timezone; // e.g., "America/New_York"

    @Column
    private String dst; // Daylight savings time rule (E, A, S, O, Z, N, U)

    @Column(name = "tz_database_time_zone")
    private String tzDatabaseTimeZone;

    @OneToMany(mappedBy = "departureAirport", cascade = CascadeType.ALL)
    private List<Flight> departingFlights;

    @OneToMany(mappedBy = "arrivalAirport", cascade = CascadeType.ALL)
    private List<Flight> arrivingFlights;

    // Additional useful fields for airport operations
    @Column
    private int numberOfGates;

    @Column
    private int numberOfTerminals;

    @Column
    private boolean hasInternationalFlights;

    @Column
    private String contactPhone;

    @Column
    private String contactEmail;

    @Column
    private String website;
}