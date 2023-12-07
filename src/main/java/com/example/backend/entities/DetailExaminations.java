package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Optional;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Detail_Examinations")
public class DetailExaminations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Officer_id")
    private Officer officerId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Examinations_id")
    private Examinations examinationsId;

    @Column(name = "role")
    private String role;

    public void setOfficer(Officer officer) {
        this.officerId = officer;
    }

    public void setExaminations(Examinations examinations) {
        this.examinationsId = examinations;
    }

}
