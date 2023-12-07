package com.example.backend.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CustomErrorMessage {
    public static String FAILED_CREATE = "Can not create";
    public static String FAILED_UPDATE = "Can not update";
    public static String NOT_FOUND_BY_ID = "Can not found by Id";
    public static String NOT_GET_ALL_LIST = "Can not get all list";
    public static String CAN_NOT_COPY_PROPERTIES =  "Can not copy properties";
    public static String VALUE_UPDATE_ILLEGAL = "Value update illegal";
    public static String CAN_NOT_UPDATE_IMAGE = "Can not update image";
    public static String CAN_NOT_UPDATE_FILE = "Can not update file";
    public static String QUANTITY_PROFILE_REGISTER_FULL = "Quantity profile register full";
}