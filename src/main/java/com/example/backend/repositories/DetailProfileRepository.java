package com.example.backend.repositories;

import com.example.backend.entities.DetailProfile;
import com.example.backend.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailProfileRepository extends JpaRepository<DetailProfile, Integer> {
}
