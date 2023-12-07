package com.example.backend.repositories;

import com.example.backend.entities.Nation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationRepository extends JpaRepository<Nation, Integer> {
}
