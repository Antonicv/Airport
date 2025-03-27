package com.example.AirportApp.service.impl;

import com.example.AirportApp.model.CrewMember;
import com.example.AirportApp.repositories.CrewMemberRepository;
import com.example.AirportApp.service.CrewMemberService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CrewMemberServiceImpl implements CrewMemberService {

    private final CrewMemberRepository crewMemberRepository;

    public CrewMemberServiceImpl(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    @Override
    public List<CrewMember> findAll() {
        return crewMemberRepository.findAll();
    }

    @Override
    public Optional<CrewMember> findById(Long id) {
        return crewMemberRepository.findById(id);
    }

    @Override
    public Optional<CrewMember> findByEmployeeId(String employeeId) {
        return crewMemberRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<CrewMember> findByFlightId(Long flightId) {
        return crewMemberRepository.findByAssignedFlightId(flightId);
    }

    @Override
    public CrewMember save(CrewMember crewMember) {
        return crewMemberRepository.save(crewMember);
    }

    @Override
    public CrewMember update(Long id, CrewMember crewMemberDetails) {
        return crewMemberRepository.findById(id)
                .map(crewMember -> {
                    crewMember.setPosition(crewMemberDetails.getPosition());
                    crewMember.setEmployeeId(crewMemberDetails.getEmployeeId());
                    // Actualizar otros campos
                    return crewMemberRepository.save(crewMember);
                })
                .orElseThrow(() -> new RuntimeException("CrewMember not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        crewMemberRepository.deleteById(id);
    }
}