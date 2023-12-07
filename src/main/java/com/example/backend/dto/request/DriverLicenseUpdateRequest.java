package com.example.backend.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DriverLicenseUpdateRequest {
    private int id;
    private LocalDate licenseDate;
    private Long code;
    private int driverLicenseClassId;
    private int driverLicenseDurationId;
}
