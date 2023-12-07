package com.example.backend.repositories;

import com.example.backend.entities.Examinations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminationRepository extends JpaRepository<Examinations, Integer>{
}
