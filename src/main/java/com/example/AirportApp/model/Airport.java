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
    private String iataCode;

    @Column(unique = true, length = 4)
    private String icaoCode;

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
    private int altitude;

    @Column
    private String timezone;

    @Column(length = 1)
    private char dst;

    @Column(name = "tz_database_time_zone")
    private String tzDatabaseTimeZone;

    // Relació OneToMany amb Flight (vols de sortida)
    @OneToMany(mappedBy = "departureAirport", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Flight> departingFlights;

    // Relació OneToMany amb Flight (vols d'arribada)
    @OneToMany(mappedBy = "arrivalAirport", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Flight> arrivingFlights;

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