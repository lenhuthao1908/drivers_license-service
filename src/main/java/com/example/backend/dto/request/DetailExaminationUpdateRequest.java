package com.example.backend.dto.request;

import lombok.Data;

@Data
public class DetailExaminationUpdateRequest {
    private int id;
    private int officerId;
    private int examinationsId;
    private String role;
}
