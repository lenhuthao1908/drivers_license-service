package com.example.backend.enums;

import java.util.Arrays;

public enum ProfileStatus {
    NOT_YET_APPROVE(0),
    APPROVED(1),
    NOT_APPROVE(2);

    private int valueStatus;

    ProfileStatus(int valueStatus) {
        this.valueStatus = valueStatus;
    }

    public int getValueStatus() {
        return valueStatus;
    }

    public static ProfileStatus fromValue(int valueStatus){
        return Arrays.stream(ProfileStatus.values())
                .filter(taskStatus -> taskStatus.getValueStatus() == valueStatus)
                .findFirst()
                .orElse(null);
    }
}
