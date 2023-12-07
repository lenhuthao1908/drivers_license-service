package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Officer")
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "office_name")
    private String name;
    @NotNull
    @Column(name = "phone")
    private String phone;
    @NotNull
    @Column(name = "email")
    private String email;

    public Officer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
