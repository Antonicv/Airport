package com.example.AirportApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "crew_members",
        uniqueConstraints = @UniqueConstraint(columnNames = "employeeNumber"))
public class CrewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "El número de empleado es obligatorio")
    @Size(min = 6, max = 10, message = "El número de empleado debe tener entre 6 y 10 caracteres")
    @Column(nullable = false, unique = true, length = 10)
    private String employeeNumber;

    @NotNull(message = "La posición es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CrewPosition position;

    @ManyToMany(mappedBy = "crewMembers",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Flight> flights = new HashSet<>();

    // Constructores
    public CrewMember() {}

    public CrewMember(String firstName, String lastName, String employeeNumber, CrewPosition position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeNumber = employeeNumber;
        this.position = position;
    }

    // Métodos de relación bidireccional
    public void addFlight(Flight flight) {
        flights.add(flight);
        flight.getCrewMembers().add(this);
    }

    public void removeFlight(Flight flight) {
        flights.remove(flight);
        flight.getCrewMembers().remove(this);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public CrewPosition getPosition() {
        return position;
    }

    public void setPosition(CrewPosition position) {
        this.position = position;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    // Equals y HashCode basado en employeeNumber
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CrewMember)) return false;
        CrewMember that = (CrewMember) o;
        return employeeNumber.equals(that.employeeNumber);
    }

    @Override
    public int hashCode() {
        return employeeNumber.hashCode();
    }

    @Override
    public String toString() {
        return "CrewMember{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", position=" + position +
                '}';
    }
}