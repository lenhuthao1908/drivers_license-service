package com.example.backend.dto.request;

import com.example.backend.entities.DriverLicenseClass;
import com.example.backend.entities.DriverLicenseDuration;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class DriverLicenseCreateRequest {
    private LocalDate licenseDate;
    private int driverLicenseClassId;
    private int driverLicenseDurationId;
}
