package com.example.AirportApp.repository;

import com.example.AirportApp.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Repositori per a la gestió d'aeroports a la base de dades
 * Extén JpaRepository que proporciona operacions CRUD bàsiques
 */
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    /**
     * Cerca un aeroport pel seu codi IATA
     * @param code Codi IATA de l'aeroport (ex: BCN, MAD)
     * @return L'aeroport trobat o null si no existeix
     */
    Airport findByCode(String code);

    /**
     * Comprova si existeix un aeroport amb un codi IATA específic
     * @param code Codi IATA a verificar
     * @return true si existeix, false si no
     */
    boolean existsByCode(String code);

    /**
     * Cerca aeroports per país
     * @param country País on es troben els aeroports
     * @return Llista d'aeroports al país especificat
     */
    List<Airport> findByCountry(String country);
}