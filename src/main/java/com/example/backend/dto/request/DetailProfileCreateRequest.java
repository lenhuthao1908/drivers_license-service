package com.example.backend.dto.request;

import com.example.backend.entities.DriverLicense;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class DetailProfileCreateRequest {
//    private LocalDate dateExamination;
    private Double resultTheoretical;
    private Double resultPractice;
    private int profileId;

    @Nullable
    private int driverLicenseId;



    public DriverLicense setDriverLicenseId() {
        return null;
    }
}
