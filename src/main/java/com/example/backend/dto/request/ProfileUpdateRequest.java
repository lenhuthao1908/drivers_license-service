package com.example.backend.dto.request;

import com.example.backend.entities.Examinations;
import com.example.backend.entities.Nation;
import com.example.backend.entities.Religion;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProfileUpdateRequest {
    private String id;
    private String email;
    private String name;
    private LocalDate dateofbirth;
    private String sex;
    private String idcard;
    private String phone;
    private String note;
    private int nationId;
    private int religionId;
    private String province;
    private String district;
    private String wards;
    private int examinationsId;
    private String profileStatus;
}
