package com.example.backend.repositories;

import com.example.backend.entities.DriverLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverLicenseRepository extends JpaRepository<DriverLicense, Integer> {
}
