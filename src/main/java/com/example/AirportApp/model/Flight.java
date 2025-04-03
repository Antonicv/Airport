package com.example.AirportApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "flights",
        uniqueConstraints = @UniqueConstraint(columnNames = "flightNumber"))
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de vuelo es obligatorio")
    @Pattern(regexp = "^[A-Z]{2,3}\\d{3,4}$", message = "Formato de vuelo inválido (ej: AA123, BA1234)")
    @Column(nullable = false, unique = true, length = 10)
    private String flightNumber;

    @NotNull(message = "La hora de salida es obligatoria")
    @Future(message = "La hora de salida debe ser en el futuro")
    @Column(nullable = false)
    private LocalDateTime departureTime;

    @NotNull(message = "La hora de llegada es obligatoria")
    @Future(message = "La hora de llegada debe ser en el futuro")
    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

    @OneToMany(mappedBy = "flight",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "flight_crew_members",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "crew_member_id"))
    private Set<CrewMember> crewMembers = new HashSet<>();

    // Constructores
    public Flight() {}

    public Flight(String flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime,
                  Airport departureAirport, Airport arrivalAirport, Airplane airplane) {
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.airplane = airplane;
        validateDates();
    }

    // Validación de fechas
    private void validateDates() {
        if (arrivalTime.isBefore(departureTime)) {
            throw new IllegalArgumentException("La hora de llegada no puede ser anterior a la de salida");
        }
    }

    // Métodos de gestión de relaciones
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setFlight(this);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setFlight(null);
    }

    public void addCrewMember(CrewMember crewMember) {
        crewMembers.add(crewMember);
        crewMember.getFlights().add(this);
    }

    public void removeCrewMember(CrewMember crewMember) {
        crewMembers.remove(crewMember);
        crewMember.getFlights().remove(this);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
        validateDates();
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
        validateDates();
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    public Set<CrewMember> getCrewMembers() {
        return Collections.unmodifiableSet(crewMembers);
    }

    // Métodos de negocio
    public boolean isInternational() {
        return !departureAirport.getCountry().equals(arrivalAirport.getCountry());
    }

    public long getFlightDurationInMinutes() {
        return java.time.Duration.between(departureTime, arrivalTime).toMinutes();
    }

    // Equals y HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return flightNumber.equals(flight.flightNumber);
    }

    @Override
    public int hashCode() {
        return flightNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", departureAirport=" + departureAirport.getCode() +
                ", arrivalAirport=" + arrivalAirport.getCode() +
                '}';
    }
}