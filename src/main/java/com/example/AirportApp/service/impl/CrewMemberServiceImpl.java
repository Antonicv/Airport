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
    public List<CrewMember> saveAll(List<CrewMember> crewMembers) {
        if (crewMembers == null || crewMembers.isEmpty()) {
            throw new AirportAppException("La lista de miembros de la tripulación no puede ser nula o vacía");
        }
        for (CrewMember crewMember : crewMembers) {
            if (crewMember.getFirstName() == null || crewMember.getFirstName().isEmpty()) {
                throw new AirportAppException("El nombre es obligatorio para cada miembro de la tripulación");
            }
            if (crewMember.getLastName() == null || crewMember.getLastName().isEmpty()) {
                throw new AirportAppException("El apellido es obligatorio para cada miembro de la tripulación");
            }
            if (crewMember.getEmployeeId() == null || crewMember.getEmployeeId().isEmpty()) {
                throw new AirportAppException("El ID de empleado es obligatorio para cada miembro de la tripulación");
            }
            if (crewMember.getPosition() == null || crewMember.getPosition().isEmpty()) {
                throw new AirportAppException("La posición es obligatoria para cada miembro de la tripulación");
            }
        }
        return crewMemberRepository.saveAll(crewMembers);
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
    public List<CrewMember> updateAll(List<CrewMember> crewMembers) {
        if (crewMembers == null || crewMembers.isEmpty()) {
            throw new AirportAppException("La lista de miembros de la tripulación no puede ser nula o vacía");
        }
        for (CrewMember crewMember : crewMembers) {
            if (crewMember.getId() == null || !crewMemberRepository.existsById(crewMember.getId())) {
                throw new AirportAppException("No se encontró un miembro de la tripulación con el ID proporcionado: " + crewMember.getId());
            }
            if (crewMember.getFirstName() != null && crewMember.getFirstName().isEmpty()) {
                throw new AirportAppException("El nombre no puede estar vacío");
            }
            if (crewMember.getLastName() != null && crewMember.getLastName().isEmpty()) {
                throw new AirportAppException("El apellido no puede estar vacío");
            }
            if (crewMember.getEmployeeId() != null && crewMember.getEmployeeId().isEmpty()) {
                throw new AirportAppException("El ID de empleado no puede estar vacío");
            }
            if (crewMember.getPosition() != null && crewMember.getPosition().isEmpty()) {
                throw new AirportAppException("La posición no puede estar vacía");
            }
        }
        return crewMemberRepository.saveAll(crewMembers); // `saveAll` actualiza si los IDs existen
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