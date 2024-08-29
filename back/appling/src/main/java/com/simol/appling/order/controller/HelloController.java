package com.simol.appling.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Auth API", description = "Appling API Documentation")
public class HelloController {

    @Operation(summary = "hello", description = "hello api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> index() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Hello, World!");
        return ResponseEntity.ok(map);
    }
}
