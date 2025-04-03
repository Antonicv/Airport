package com.example.AirportApp.repository;

import com.example.AirportApp.model.Flight;
import com.example.AirportApp.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

public interface FlightCrewRepository extends JpaRepository<Flight, Long> {

    @Transactional
    @Modifying
    @Query("INSERT INTO Flight_Crew_Member (flight_id, crew_member_id) VALUES (:flightId, :crewMemberId)")
    void addCrewMemberToFlight(@Param("flightId") Long flightId,
                               @Param("crewMemberId") Long crewMemberId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Flight_Crew_Member WHERE flight_id = :flightId AND crew_member_id = :crewMemberId")
    void removeCrewMemberFromFlight(@Param("flightId") Long flightId,
                                    @Param("crewMemberId") Long crewMemberId);
}