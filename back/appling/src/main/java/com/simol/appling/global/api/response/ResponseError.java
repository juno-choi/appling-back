package com.simol.appling.global.api.response;

import lombok.Builder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Builder
public record ResponseError(
    String detail,
    String message
) {
    public static ResponseError from(ObjectError f) {
        return ResponseError.builder()
                .detail(fieldConvertToSnakeCase(((FieldError) f).getField()))
                .message(f.getDefaultMessage())
                .build();
    }

    private static String fieldConvertToSnakeCase(String field) {
        return field.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
