package com.example.backend.dto.response;

import com.example.backend.entities.Examinations;
import com.example.backend.entities.Nation;
import com.example.backend.entities.Religion;
import jakarta.annotation.Nullable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class DetailProfileResponse {
    private int id;
    private LocalDate dateExamination;
    private Double resultTheoretical;
    private Double resultPractice;
    private String email;
    private String name;
    private String sex;
    private String idcard;
    private String phone;
    private String image;
    private String note;
    private String nationName;
    private String religionName;
    private String province;
    private String district;
    private String wards;
    private int profileId;

    private String examinationsName;
//    private String examinationsOfficer;

    private String driverLicenseClass;

    private String driverLicenseDuration;

    private String result;
}
