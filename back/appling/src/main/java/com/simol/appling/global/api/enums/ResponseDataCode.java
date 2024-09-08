package com.simol.appling.global.api.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseDataCode {
    SUCCESS("0000", "success"),
    CREATE("0001", "create"),
    BAD_REQUEST("0400", "bad request"),
    ;
    public String code;
    public String message;
}
