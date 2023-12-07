package com.example.backend.utils;

import com.example.backend.exceptions.CustomErrorMessage;

import java.lang.reflect.Field;

public class NonNullPropertiesUtils {
    public static void copyNonNullProperties(Object request, Object response) {
        Class<?> requestClass = request.getClass();
        Class<?> responseClass = response.getClass();

        for (Field requestField : requestClass.getDeclaredFields()) {
            try {
                requestField.setAccessible(true);
                Object requestValue = requestField.get(request);

                if (requestValue != null && requestValue != "") {
                    Field responseField = responseClass.getDeclaredField(requestField.getName());
                    responseField.setAccessible(true);
                    responseField.set(response, requestValue);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(CustomErrorMessage.CAN_NOT_COPY_PROPERTIES);
            }
        }
    }
}
