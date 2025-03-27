package com.example.AirportApp.service.impl;

import com.example.AirportApp.model.Booking;
import com.example.AirportApp.repositories.BookingRepository;
import com.example.AirportApp.service.BookingService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Optional<Booking> findByBookingReference(String bookingReference) {
        return bookingRepository.findByBookingReference(bookingReference);
    }

    @Override
    public List<Booking> findByPassengerId(Long passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
    }

    @Override
    public Booking save(Booking booking) {
        // Aquí podrías añadir lógica de negocio, como validaciones
        return bookingRepository.save(booking);
    }

    @Override
    public Booking update(Long id, Booking bookingDetails) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    booking.setStatus(bookingDetails.getStatus());
                    booking.setNumberOfSeats(bookingDetails.getNumberOfSeats());
                    // Actualizar otros campos
                    return bookingRepository.save(booking);
                })
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }
}