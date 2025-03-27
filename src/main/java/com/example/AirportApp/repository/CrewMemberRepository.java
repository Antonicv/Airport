package com.example.AirportApp.repositories;

import com.example.AirportApp.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    Optional<CrewMember> findByEmployeeId(String employeeId);
    List<CrewMember> findByPosition(String position);
    List<CrewMember> findByNationality(String nationality);

    @Query("SELECT c FROM CrewMember c JOIN c.assignedFlights f WHERE f.id = ?1")
    List<CrewMember> findByAssignedFlightId(Long flightId);

    @Query("SELECT c FROM CrewMember c WHERE c.position = 'PILOT' OR c.position = 'CO_PILOT'")
    List<CrewMember> findAllPilots();
}