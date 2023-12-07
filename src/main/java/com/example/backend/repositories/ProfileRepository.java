package com.example.backend.repositories;


import com.example.backend.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    void deleteById(int id);

    void delete(Profile profile);

    List<Profile> findAllByidcard(String idCard);

    @Query("SELECT p FROM Profile p WHERE p.name = :name")
    List<Profile> findAllByName(@Param("name") String name);
}