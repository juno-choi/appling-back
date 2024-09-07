package com.simol.appling.global.api.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseDataCode {
    SUCCESS("0000", "success"),
    ;
    public String code;
    public String message;
}
