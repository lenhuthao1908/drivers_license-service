package com.example.backend.dto.request;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class DetailProfileUpdateRequest {
    private int id;
    private Double resultTheoretical;
    private Double resultPractice;
    private int profileId;
    @Nullable
    private int driverLicenseId;

    private String result;

    public void setDriverLicenseId(Integer o) {
        if (o == null) {
            this.driverLicenseId = 0;
        } else {
            this.driverLicenseId = o;
        }
    }

    public Double getResultTheoretical() {
        return resultTheoretical;
    }

    public void setResultTheoretical(Double resultTheoretical) {
        if (resultTheoretical != null) {
            this.resultTheoretical = resultTheoretical;
        }
    }

    public Double getResultPractice() {
        return resultPractice;
    }

    public void setResultPractice(Double resultPractice) {
        if (resultTheoretical != null) {
            this.resultPractice = resultPractice;
        }
    }
}
