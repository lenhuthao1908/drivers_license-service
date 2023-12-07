package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class DriverLicenseResponse {
    private int id;
    private LocalDate licenseDate;
    private long code;
    private String driverLicenseClassName;
    private String driverLicenseDurationName;
}
