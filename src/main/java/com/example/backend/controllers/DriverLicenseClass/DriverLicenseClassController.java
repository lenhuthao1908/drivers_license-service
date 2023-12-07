package com.example.backend.controllers.DriverLicenseClass;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DriverLicenseClass;
import com.example.backend.services.DriverLicenseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/driverlicenseclass")
public class DriverLicenseClassController {
    @Autowired
    private DriverLicenseClassService driverLicenseClassService;

    @PostMapping("")
    public ResponseEntity<DriverLicenseClass> createDriverLicenseClass(@RequestBody DriverLicenseClass driverLicenseClass) {
        return new ResponseEntity<>(driverLicenseClassService.createDriverLicenseClass(driverLicenseClass), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<DriverLicenseClass>> updateDriverLicenseClass(@RequestBody DriverLicenseClass driverLicenseClass, @PathVariable(name = "id") int id) {
        driverLicenseClass.setId(id);
        return ResponseEntity.ok(driverLicenseClassService.updateDriverLicenseClass(driverLicenseClass));
    }

    @GetMapping("")
    public ResponseEntity<List<DriverLicenseClass>> DriverLicenseClassList() {
        return ResponseEntity.ok(driverLicenseClassService.DriverLicenseClassList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DriverLicenseClass>> findDriverLicenseClassById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(driverLicenseClassService.findDriverLicenseClassById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteDriverLicenseClass(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(driverLicenseClassService.deleteDriverLicenseClassById(id));
    }

}
