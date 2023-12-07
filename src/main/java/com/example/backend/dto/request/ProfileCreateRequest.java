package com.example.backend.dto.request;

import com.example.backend.entities.Examinations;
import com.example.backend.entities.Nation;
import com.example.backend.entities.Religion;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class ProfileCreateRequest {
    private String email;
    private String name;
    private LocalDate dateofbirth;
    private String sex;
    private String idcard;
    private String phone;
    private String image;
    private String file;
    private String note;
    private int nationId;
    private int religionId;
    private String province;
    private String district;
    private String wards;
    private int examinationsId;

    public void setNationId() {
    }
}
