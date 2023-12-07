package com.example.backend.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Detail_Profile")
public class DetailProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @JoinColumn(name = "result_theoretical")
    private Double resultTheoretical;
    @NotNull
    @JoinColumn(name = "result_practice")
    private Double resultPractice;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private Profile profileId;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_license_id")
    private DriverLicense driverLicenseId;


    @Column(name= "result")
    private String result;



    public void setProfile(Profile profile) {
        this.profileId = profile;
    }

    public void setDriverLicenseId(DriverLicense driverLicense) {
        this.driverLicenseId=driverLicense;
    }

    public void setDriverLicenseId(int driverLicenseId) {

    }
}
