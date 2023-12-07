package com.example.backend.repositories;

import com.example.backend.entities.Religion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReligionRepository extends JpaRepository<Religion, Integer> {
}
