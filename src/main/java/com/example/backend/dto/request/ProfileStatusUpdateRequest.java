package com.example.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProfileStatusUpdateRequest {
    @JsonProperty("profileStatus")
    private String profileStatus;

    @JsonCreator
    public ProfileStatusUpdateRequest(@JsonProperty("profileStatus") String profileStatus) {
        this.profileStatus = profileStatus;
    }
}
