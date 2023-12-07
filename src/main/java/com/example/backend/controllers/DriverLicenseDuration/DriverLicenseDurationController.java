package com.example.backend.controllers.DriverLicenseDuration;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DriverLicenseDuration;
import com.example.backend.services.DriverLicenseDurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/driverlicenseduration")
public class DriverLicenseDurationController {
    @Autowired
    private DriverLicenseDurationService driverLicenseDurationService;

    @PostMapping("")
    public ResponseEntity<DriverLicenseDuration> createDriverLicenseDuration(@RequestBody DriverLicenseDuration DriverLicenseDuration) {
        return new ResponseEntity<>(driverLicenseDurationService.createDriverLicenseDuration(DriverLicenseDuration), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<DriverLicenseDuration>> updateDriverLicenseDuration(@RequestBody DriverLicenseDuration driverLicenseDuration, @PathVariable(name = "id") int id) {
        driverLicenseDuration.setId(id);
        return ResponseEntity.ok(driverLicenseDurationService.updateDriverLicenseDuration(driverLicenseDuration));
    }

    @GetMapping("")
    public ResponseEntity<List<DriverLicenseDuration>> DriverLicenseDurationList() {
        return ResponseEntity.ok(driverLicenseDurationService.DriverLicenseDurationList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DriverLicenseDuration>> findDriverLicenseDurationById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(driverLicenseDurationService.findDriverLicenseDurationById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteDriverLicenseDuration(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(driverLicenseDurationService.deleteDriverLicenseDurationById(id));
    }

}
