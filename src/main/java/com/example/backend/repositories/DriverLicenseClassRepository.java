package com.example.backend.repositories;

import com.example.backend.entities.DriverLicenseClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverLicenseClassRepository extends JpaRepository<DriverLicenseClass, Integer> {
}
