package com.example.AirportApp.service;

import com.example.AirportApp.model.*;
import com.example.AirportApp.repository.CrewMemberRepository;
import com.example.AirportApp.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CrewMemberService {
    private final CrewMemberRepository crewMemberRepository;
    private final FlightRepository flightRepository;

    public CrewMemberService(CrewMemberRepository crewMemberRepository,
                             FlightRepository flightRepository) {
        this.crewMemberRepository = crewMemberRepository;
        this.flightRepository = flightRepository;
    }

    public List<CrewMember> findAll() {
        return crewMemberRepository.findAll();
    }

    public Optional<CrewMember> findById(Long id) {
        return crewMemberRepository.findById(id);
    }

    public CrewMember save(CrewMember crewMember) {
        return crewMemberRepository.save(crewMember);
    }

    public CrewMember update(Long id, CrewMember crewMemberDetails) {
        return crewMemberRepository.findById(id)
                .map(crewMember -> {
                    crewMember.setName(crewMemberDetails.getName());
                    crewMember.setPosition(crewMemberDetails.getPosition());
                    crewMember.setLicenseNumber(crewMemberDetails.getLicenseNumber());
                    return crewMemberRepository.save(crewMember);
                })
                .orElseThrow(() -> new RuntimeException("Crew member not found with id: " + id));
    }

    public void delete(Long id) {
        crewMemberRepository.deleteById(id);
    }

    public List<Flight> findFlightsByCrewMemberId(Long crewMemberId) {
        return flightRepository.findByCrewMembersId(crewMemberId);
    }

    public List<CrewMember> findAvailableCrewMembers(LocalDateTime start, LocalDateTime end) {
        return crewMemberRepository.findAvailableCrewMembers(start, end);
    }

    public void assignToFlight(Long crewMemberId, Long flightId) {
        CrewMember crewMember = crewMemberRepository.findById(crewMemberId)
                .orElseThrow(() -> new RuntimeException("Crew member not found"));

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (!flight.getCrewMembers().contains(crewMember)) {
            flight.getCrewMembers().add(crewMember);
            flightRepository.save(flight);
        }
    }

    public void removeFromFlight(Long crewMemberId, Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.getCrewMembers().removeIf(cm -> cm.getId().equals(crewMemberId));
        flightRepository.save(flight);
    }
}