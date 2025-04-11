// PassengerServiceImpl.java
package com.example.AirportApp.service.impl;

import com.example.AirportApp.exception.AirportAppException;
import com.example.AirportApp.model.Passenger;
import com.example.AirportApp.repository.PassengerRepository;
import com.example.AirportApp.service.PassengerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Passenger> findById(Long id) {
        if (id == null) {
            throw new AirportAppException("ID no pot ser null");
        }
        return passengerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Passenger> findByPassportNumber(String passportNumber) {
        if (passportNumber == null || passportNumber.isEmpty()) {
            throw new AirportAppException("El número de passaport no pot estar buit");
        }
        return passengerRepository.findByPassportNumber(passportNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Passenger> findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new AirportAppException("L'email no pot estar buit");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new AirportAppException("Format d'email invàlid");
        }
        return passengerRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Passenger save(Passenger passenger) {
        if (passenger == null) {
            throw new AirportAppException("El passatger no pot ser null");
        }
        if (passenger.getFirstName() == null || passenger.getFirstName().isEmpty()) {
            throw new AirportAppException("El nom és obligatori");
        }
        if (passenger.getLastName() == null || passenger.getLastName().isEmpty()) {
            throw new AirportAppException("El cognom és obligatori");
        }
        if (passenger.getPassportNumber() == null || passenger.getPassportNumber().isEmpty()) {
            throw new AirportAppException("El número de passaport és obligatori");
        }
        if (passenger.getEmail() == null || passenger.getEmail().isEmpty()) {
            throw new AirportAppException("L'email és obligatori");
        }
        if (!EMAIL_PATTERN.matcher(passenger.getEmail()).matches()) {
            throw new AirportAppException("Format d'email invàlid");
        }
        return passengerRepository.save(passenger);
    }

    @Override
    @Transactional
    public Passenger update(Long id, Passenger passengerDetails) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    if (passengerDetails.getFirstName() != null && !passengerDetails.getFirstName().isEmpty()) {
                        passenger.setFirstName(passengerDetails.getFirstName());
                    }
                    if (passengerDetails.getLastName() != null && !passengerDetails.getLastName().isEmpty()) {
                        passenger.setLastName(passengerDetails.getLastName());
                    }
                    if (passengerDetails.getPassportNumber() != null && !passengerDetails.getPassportNumber().isEmpty()) {
                        passenger.setPassportNumber(passengerDetails.getPassportNumber());
                    }
                    if (passengerDetails.getEmail() != null && !passengerDetails.getEmail().isEmpty()) {
                        if (!EMAIL_PATTERN.matcher(passengerDetails.getEmail()).matches()) {
                            throw new AirportAppException("Format d'email invàlid");
                        }
                        passenger.setEmail(passengerDetails.getEmail());
                    }
                    if (passengerDetails.getPhoneNumber() != null) {
                        passenger.setPhoneNumber(passengerDetails.getPhoneNumber());
                    }
                    if (passengerDetails.getNationality() != null) {
                        passenger.setNationality(passengerDetails.getNationality());
                    }
                    return passengerRepository.save(passenger);
                })
                .orElseThrow(() -> new AirportAppException("Passatger no trobat amb id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new AirportAppException("Passatger no trobat amb id: " + id);
        }
        passengerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Passenger> saveAll(List<Passenger> passengers) {
        if (passengers == null || passengers.isEmpty()) {
            throw new AirportAppException("La lista de pasajeros no puede ser nula o vacía");
        }
        for (Passenger passenger : passengers) {
            if (passenger.getFirstName() == null || passenger.getFirstName().isEmpty()) {
                throw new AirportAppException("El nombre es obligatorio para cada pasajero");
            }
            if (passenger.getLastName() == null || passenger.getLastName().isEmpty()) {
                throw new AirportAppException("El apellido es obligatorio para cada pasajero");
            }
            if (passenger.getPassportNumber() == null || passenger.getPassportNumber().isEmpty()) {
                throw new AirportAppException("El número de pasaporte es obligatorio para cada pasajero");
            }
            if (passenger.getEmail() == null || passenger.getEmail().isEmpty()) {
                throw new AirportAppException("El email es obligatorio para cada pasajero");
            }
            if (!EMAIL_PATTERN.matcher(passenger.getEmail()).matches()) {
                throw new AirportAppException("Formato de email inválido para el pasajero");
            }
        }
        return passengerRepository.saveAll(passengers);
    }

    @Override
    @Transactional
    public List<Passenger> updateAll(List<Passenger> passengers) {
        if (passengers == null || passengers.isEmpty()) {
            throw new AirportAppException("La lista de pasajeros no puede ser nula o vacía");
        }
        for (Passenger passenger : passengers) {
            if (passenger.getId() == null || !passengerRepository.existsById(passenger.getId())) {
                throw new AirportAppException("No se encontró un pasajero con el ID proporcionado: " + passenger.getId());
            }
            if (passenger.getEmail() != null && !EMAIL_PATTERN.matcher(passenger.getEmail()).matches()) {
                throw new AirportAppException("Formato de email inválido para el pasajero con ID: " + passenger.getId());
            }
        }
        return passengerRepository.saveAll(passengers); // `saveAll` actualiza si los IDs existen
    }
}