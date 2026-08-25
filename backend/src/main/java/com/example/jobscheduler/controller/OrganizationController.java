package com.example.jobscheduler.controller;

import com.example.jobscheduler.entity.Organization;
import com.example.jobscheduler.repository.OrganizationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationRepository orgRepo;

    public OrganizationController(OrganizationRepository orgRepo) {
        this.orgRepo = orgRepo;
    }

    @PostMapping
    public ResponseEntity<Organization> create(@Valid @RequestBody Organization organization) {
        Organization saved = orgRepo.save(organization);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Organization>> list() {
        return ResponseEntity.ok(orgRepo.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organization> update(@PathVariable Long id, @RequestBody Organization organization) {
        organization.setId(id);
        return ResponseEntity.ok(orgRepo.save(organization));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orgRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
