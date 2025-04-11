// CrewMemberService.java
package com.example.AirportApp.service;

import com.example.AirportApp.model.CrewMember;
import java.util.List;
import java.util.Optional;

public interface CrewMemberService {
    List<CrewMember> findAll();
    Optional<CrewMember> findById(Long id);
    Optional<CrewMember> findByEmployeeId(String employeeId);
    List<CrewMember> findByFlightId(Long flightId);
    CrewMember save(CrewMember crewMember);
    List<CrewMember> saveAll(List<CrewMember> crewMembers);
    CrewMember update(Long id, CrewMember crewMemberDetails);
    List<CrewMember> updateAll(List<CrewMember> crewMembers);
    void deleteById(Long id);
}