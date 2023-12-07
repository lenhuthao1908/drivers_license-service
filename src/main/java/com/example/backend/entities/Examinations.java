package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Examinations")
public class Examinations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "examinations_name")
    private String examinationsName;
    @NotNull
    @Column(name = "examinations_date")
    private LocalDate examinationsDate;
    @NotNull
    @Column(name = "examinations_description")
    private String examinationsDescription;
    @NotNull
    @Column(name = "examinations_quantity")
    private int examinationsQuantity;

    public Examinations(String examinationsName, LocalDate examinationsDate, String examinationsDescription, int examinationsQuantity) {
        this.examinationsName = examinationsName;
        this.examinationsDate = examinationsDate;
        this.examinationsDescription = examinationsDescription;
        this.examinationsQuantity = examinationsQuantity;
    }
}
