package com.simol.appling.global.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.global.api.enums.ResponseDataCode;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ResponseData<T>(
    String code,
    String message,
    T data
) {
    public static ResponseData from(ResponseDataCode responseDataCode, Object data) {
        return ResponseData.<Object>builder()
                .code(responseDataCode.code)
                .message(responseDataCode.message)
                .data(data)
                .build();
    }
}
