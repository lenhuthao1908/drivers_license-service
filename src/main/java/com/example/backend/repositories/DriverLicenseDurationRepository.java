package com.example.backend.repositories;

import com.example.backend.entities.DriverLicenseDuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverLicenseDurationRepository extends JpaRepository<DriverLicenseDuration, Integer> {
}
