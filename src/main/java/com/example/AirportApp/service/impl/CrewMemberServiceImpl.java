// CrewMemberServiceImpl.java
package com.example.AirportApp.service.impl;

import com.example.AirportApp.exception.AirportAppException;
import com.example.AirportApp.model.CrewMember;
import com.example.AirportApp.repository.CrewMemberRepository;
import com.example.AirportApp.service.CrewMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CrewMemberServiceImpl implements CrewMemberService {

    private final CrewMemberRepository crewMemberRepository;

    public CrewMemberServiceImpl(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CrewMember> findAll() {
        return crewMemberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CrewMember> findById(Long id) {
        if (id == null) {
            throw new AirportAppException("ID no pot ser null");
        }
        return crewMemberRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CrewMember> findByEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.isEmpty()) {
            throw new AirportAppException("L'ID d'empleat no pot estar buit");
        }
        return crewMemberRepository.findByEmployeeId(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CrewMember> findByFlightId(Long flightId) {
        if (flightId == null) {
            throw new AirportAppException("ID de vol no pot ser null");
        }
        return crewMemberRepository.findByAssignedFlightsId(flightId);
    }

    @Override
    @Transactional
    public CrewMember save(CrewMember crewMember) {
        if (crewMember == null) {
            throw new AirportAppException("El membre de la tripulació no pot ser null");
        }
        if (crewMember.getFirstName() == null || crewMember.getFirstName().isEmpty()) {
            throw new AirportAppException("El nom és obligatori");
        }
        if (crewMember.getLastName() == null || crewMember.getLastName().isEmpty()) {
            throw new AirportAppException("El cognom és obligatori");
        }
        if (crewMember.getEmployeeId() == null || crewMember.getEmployeeId().isEmpty()) {
            throw new AirportAppException("L'ID d'empleat és obligatori");
        }
        if (crewMember.getPosition() == null || crewMember.getPosition().isEmpty()) {
            throw new AirportAppException("La posició és obligatòria");
        }
        return crewMemberRepository.save(crewMember);
    }

    @Override
    @Transactional
    public CrewMember update(Long id, CrewMember crewMemberDetails) {
        return crewMemberRepository.findById(id)
                .map(crewMember -> {
                    if (crewMemberDetails.getFirstName() != null && !crewMemberDetails.getFirstName().isEmpty()) {
                        crewMember.setFirstName(crewMemberDetails.getFirstName());
                    }
                    if (crewMemberDetails.getLastName() != null && !crewMemberDetails.getLastName().isEmpty()) {
                        crewMember.setLastName(crewMemberDetails.getLastName());
                    }
                    if (crewMemberDetails.getEmployeeId() != null && !crewMemberDetails.getEmployeeId().isEmpty()) {
                        crewMember.setEmployeeId(crewMemberDetails.getEmployeeId());
                    }
                    if (crewMemberDetails.getPosition() != null && !crewMemberDetails.getPosition().isEmpty()) {
                        crewMember.setPosition(crewMemberDetails.getPosition());
                    }
                    if (crewMemberDetails.getNationality() != null) {
                        crewMember.setNationality(crewMemberDetails.getNationality());
                    }
                    return crewMemberRepository.save(crewMember);
                })
                .orElseThrow(() -> new AirportAppException("Membre de tripulació no trobat amb id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!crewMemberRepository.existsById(id)) {
            throw new AirportAppException("Membre de tripulació no trobat amb id: " + id);
        }
        crewMemberRepository.deleteById(id);
    }
}