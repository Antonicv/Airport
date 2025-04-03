package com.example.AirportApp.controller;

import com.example.AirportApp.model.CrewMember;
import com.example.AirportApp.service.CrewMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crew-members")
public class CrewMemberController {
    private final CrewMemberService crewMemberService;

    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @GetMapping
    public List<CrewMember> getAllCrewMembers() {
        return crewMemberService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrewMember> getCrewMemberById(@PathVariable Long id) {
        return crewMemberService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/flights")
    public ResponseEntity<List<Flight>> getCrewMemberFlights(@PathVariable Long id) {
        return ResponseEntity.ok(crewMemberService.findFlightsByCrewMemberId(id));
    }

    @PostMapping
    public CrewMember createCrewMember(@RequestBody CrewMember crewMember) {
        return crewMemberService.save(crewMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CrewMember> updateCrewMember(@PathVariable Long id, @RequestBody CrewMember crewMember) {
        return ResponseEntity.ok(crewMemberService.update(id, crewMember));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrewMember(@PathVariable Long id) {
        crewMemberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}