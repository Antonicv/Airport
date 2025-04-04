// BookingServiceImpl.java
package com.example.AirportApp.service.impl;

import com.example.AirportApp.exception.AirportAppException;
import com.example.AirportApp.model.Booking;
import com.example.AirportApp.model.Booking.BookingStatus;
import com.example.AirportApp.repository.BookingRepository;
import com.example.AirportApp.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findById(Long id) {
        if (id == null) {
            throw new AirportAppException("ID no pot ser null");
        }
        return bookingRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findByBookingReference(String bookingReference) {
        if (bookingReference == null || bookingReference.isEmpty()) {
            throw new AirportAppException("La referència de reserva no pot estar buida");
        }
        return bookingRepository.findByBookingReference(bookingReference);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByPassengerId(Long passengerId) {
        if (passengerId == null) {
            throw new AirportAppException("ID de passatger no pot ser null");
        }
        return bookingRepository.findByPassengerId(passengerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByFlightId(Long flightId) {
        if (flightId == null) {
            throw new AirportAppException("ID de vol no pot ser null");
        }
        return bookingRepository.findByFlightId(flightId);
    }

    @Override
    @Transactional
    public Booking save(Booking booking) {
        if (booking == null) {
            throw new AirportAppException("La reserva no pot ser null");
        }
        if (booking.getPassenger() == null) {
            throw new AirportAppException("El passatger és obligatori");
        }
        if (booking.getFlight() == null) {
            throw new AirportAppException("El vol és obligatori");
        }
        if (booking.getNumberOfSeats() <= 0) {
            throw new AirportAppException("El nombre de seients ha de ser major que zero");
        }
        if (booking.getTotalPrice() == null || booking.getTotalPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AirportAppException("El preu total ha de ser major que zero");
        }
        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.CONFIRMED);
        }
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking update(Long id, Booking bookingDetails) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    if (bookingDetails.getStatus() != null) {
                        booking.setStatus(bookingDetails.getStatus());
                    }
                    if (bookingDetails.getNumberOfSeats() > 0) {
                        booking.setNumberOfSeats(bookingDetails.getNumberOfSeats());
                    }
                    if (bookingDetails.getTotalPrice() != null &&
                            bookingDetails.getTotalPrice().compareTo(BigDecimal.ZERO) > 0) {
                        booking.setTotalPrice(bookingDetails.getTotalPrice());
                    }
                    return bookingRepository.save(booking);
                })
                .orElseThrow(() -> new AirportAppException("Reserva no trobada amb id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new AirportAppException("Reserva no trobada amb id: " + id);
        }
        bookingRepository.deleteById(id);
    }
}