package com.example.backend.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
public class DetailExaminationsResponse {
    private int id;
    private int officerId;
    private int examinationsId;
    private String name;
    private String phone;
    private String email;
    private String examinationsName;
    private LocalDate examinationsDate;
    private String examinationsDescription;
    private String role;
}
