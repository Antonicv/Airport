package com.example.AirportApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"flight_id", "seatNumber"}),
                @UniqueConstraint(columnNames = "bookingReference")
        })
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Corregido: IDENTITY en lugar de IDENTY
    private Long id;

    @NotBlank(message = "La referencia de reserva es obligatoria")
    @Size(min = 8, max = 8, message = "La referencia debe tener 8 caracteres")
    @Column(nullable = false, unique = true, length = 8)
    private String bookingReference;

    @NotNull(message = "La fecha de reserva es obligatoria")
    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @NotBlank(message = "El número de asiento es obligatorio")
    @Pattern(regexp = "^[A-Za-z]\\d{1,3}$", message = "Formato de asiento inválido (ej: A1, B12)")
    @Column(nullable = false, length = 5)
    private String seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", nullable = false) // Corregido: @JoinColumn en lugar de @JoinColumn
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    // Constructores
    public Booking() {
        this.bookingDate = LocalDateTime.now();
        this.bookingReference = generateBookingRef();
    }

    public Booking(Passenger passenger, Flight flight, String seatNumber) {
        this();
        this.passenger = passenger;
        this.flight = flight;
        this.seatNumber = seatNumber;
    }

    // Método para generar referencia (ahora es protected para posible extensión)
    protected String generateBookingRef() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    // Equals y HashCode basado en bookingReference
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return bookingReference.equals(booking.bookingReference);
    }

    @Override
    public int hashCode() {
        return bookingReference.hashCode();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", bookingReference='" + bookingReference + '\'' +
                ", bookingDate=" + bookingDate +
                ", seatNumber='" + seatNumber + '\'' +
                '}';
    }
}