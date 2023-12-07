package com.example.backend.enums;

import lombok.Data;

import java.util.Arrays;

public enum ProfileType {
    NEW_PROFILE(0),
    REVERSE_PROFILE(1);

    private int valueType;

    ProfileType(int valueType) {
        this.valueType = valueType;
    }

    public int getValueType() {
        return valueType;
    }

    public static ProfileType fromValue(int valueType){
        return Arrays.stream(ProfileType.values())
                .filter(profile -> profile.getValueType() == valueType)
                .findFirst()
                .orElse(null);
    }


}
