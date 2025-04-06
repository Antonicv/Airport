package com.example.AirportApp.controllers;

import com.example.AirportApp.model.CrewMember;
import com.example.AirportApp.service.CrewMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/crew-members")
public class CrewMemberController {

    @Autowired
    private CrewMemberService crewMemberService;

    @GetMapping
    public ResponseEntity<List<CrewMember>> getAllCrewMembers() {
        return ResponseEntity.ok(crewMemberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrewMember> getCrewMemberById(@PathVariable Long id) {
        return crewMemberService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-employee-id/{employeeId}")
    public ResponseEntity<CrewMember> getCrewMemberByEmployeeId(@PathVariable String employeeId) {
        return crewMemberService.findByEmployeeId(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CrewMember> createCrewMember(@RequestBody CrewMember crewMember) {
        return ResponseEntity.ok(crewMemberService.save(crewMember));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CrewMember> updateCrewMember(@PathVariable Long id, @RequestBody CrewMember crewMemberDetails) {
        return ResponseEntity.ok(crewMemberService.update(id, crewMemberDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrewMember(@PathVariable Long id) {
        crewMemberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-flight/{flightId}")
    public ResponseEntity<List<CrewMember>> getCrewMembersByFlight(@PathVariable Long flightId) {
        return ResponseEntity.ok(crewMemberService.findByFlightId(flightId));
    }
}