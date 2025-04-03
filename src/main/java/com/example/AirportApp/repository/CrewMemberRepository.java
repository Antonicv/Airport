package com.example.AirportApp.repository;

import com.example.AirportApp.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {

    // Buscar miembros de tripulación por posición/cargo
    List<CrewMember> findByPosition(String position);

    // Miembros de tripulación disponibles en una fecha específica
    @Query("SELECT c FROM CrewMember c WHERE c.id NOT IN " +
            "(SELECT cm.id FROM Flight f JOIN f.crewMembers cm WHERE f.departureTime BETWEEN :start AND :end)")
    List<CrewMember> findAvailableCrewMembers(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);
}