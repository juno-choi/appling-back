package com.simol.appling.product.controller;

import com.simol.appling.global.api.annotation.ApiController;
import com.simol.appling.global.api.enums.ResponseDataCode;
import com.simol.appling.global.api.response.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@ApiController
public class ProductController {
    @GetMapping("/product")
    public ResponseEntity<ResponseData<Map<String, Object>>> response() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
        map.put("key2", "value2");
        return ResponseEntity.ok(ResponseData.from(ResponseDataCode.SUCCESS, map));
    }
}
