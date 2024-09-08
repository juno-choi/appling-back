package com.simol.appling.product.controller;

import com.simol.appling.global.api.annotation.ApiController;
import com.simol.appling.global.api.enums.ResponseDataCode;
import com.simol.appling.global.api.response.ResponseData;
import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.vo.PostProductResponse;
import com.simol.appling.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@ApiController
@RequiredArgsConstructor
@Tag(name = "Product API", description = "Product API Documentation")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    @Operation(summary = "상품 등록", description = "상품 등록 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "정상", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    public ResponseEntity<ResponseData<PostProductResponse>> product(@RequestBody @Validated PostProductRequest productRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseData.from(ResponseDataCode.SUCCESS, errors));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.from(ResponseDataCode.SUCCESS, productService.createProduct(productRequest)));
    }
}
