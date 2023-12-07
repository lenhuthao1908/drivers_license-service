package com.example.backend.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class DetailExaminationCreateRequest {
    private int officerId;
    private int examinationsId;
    private String role;
}
