package com.example.backend.controllers.officer;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Officer;
import com.example.backend.services.OfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/officer")
public class OfficerController {
    @Autowired
    private OfficerService officerService;

    @PostMapping()
    public ResponseEntity<Officer> createOfficer(@RequestBody Officer officer) {
        return new ResponseEntity<>(officerService.createOfficer(officer), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Officer>> updateOfficer(@RequestBody Officer officer, @PathVariable(name = "id") int id) {
        officer.setId(id);
        return ResponseEntity.ok(officerService.updateOfficer(officer));
    }

    @GetMapping()
    public ResponseEntity<List<Officer>> officerList() {
        return ResponseEntity.ok(officerService.officerList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Officer>> findOfficerById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(officerService.findOfficerById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteOfficerById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(officerService.deleteProfileById(id));
    }
}
