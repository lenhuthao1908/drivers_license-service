package com.example.backend.controllers.DriverLicense;

import com.example.backend.dto.request.DriverLicenseCreateRequest;
import com.example.backend.dto.request.DriverLicenseUpdateRequest;
import com.example.backend.dto.response.DriverLicenseResponse;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DriverLicense;
import com.example.backend.services.DriverLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/driverlicense")
public class DriverLicenseController {
    @Autowired
    private DriverLicenseService driverLicenseService;



    @PostMapping("")
    public ResponseEntity<DriverLicense> createDriverLicense(@RequestBody DriverLicenseCreateRequest driverLicenseCreateRequest) {
        return new ResponseEntity<>(driverLicenseService.createDriverLicense(driverLicenseCreateRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<DriverLicense>> updateDriverLicense(@RequestBody DriverLicenseUpdateRequest driverLicenseUpdateRequest, @PathVariable(name = "id") int id) {
        driverLicenseUpdateRequest.setId(id);
        return ResponseEntity.ok(driverLicenseService.updateDriverLicense(driverLicenseUpdateRequest));
    }

    @GetMapping("")
    public ResponseEntity<List<DriverLicenseResponse>> DriverLicenseList() {
        return ResponseEntity.ok(driverLicenseService.DriverLicenseList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DriverLicenseResponse>> findDriverLicenseById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(driverLicenseService.findDriverLicenseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteDriverLicenseById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(driverLicenseService.deleteDriverLicenseClassById(id));
    }

}
