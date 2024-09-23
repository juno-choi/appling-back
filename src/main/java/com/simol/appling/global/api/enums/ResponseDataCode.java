package com.simol.appling.global.api.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum ResponseDataCode {
    SUCCESS("0000", "success"),
    CREATE("0001", "create"),
    BAD_REQUEST("0400", "bad request"),
    ;
    public String code;
    public String message;
}
