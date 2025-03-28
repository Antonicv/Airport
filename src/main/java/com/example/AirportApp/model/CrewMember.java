package com.example.AirportApp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String employeeId;

    @Column(nullable = false)
    private String position; // Pilot, Co-Pilot, Flight Attendant, etc.

    @Column(nullable = false)
    private String nationality;

    @ManyToMany(mappedBy = "crewMembers")
    private List<Flight> assignedFlights;
}