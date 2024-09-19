package com.simol.appling.order.controller;

import com.simol.appling.global.api.annotation.ApiController;
import com.simol.appling.global.api.enums.ResponseDataCode;
import com.simol.appling.global.api.response.ResponseData;
import com.simol.appling.order.domain.dto.PostOrderRequest;
import com.simol.appling.order.domain.vo.PostOrderResponse;
import com.simol.appling.order.service.OrderService;
import com.simol.appling.product.domain.vo.PostProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiController
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API Documentation")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/order")
    @Operation(summary = "주문 등록", description = "주문 등록 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "정상", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostProductResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    public ResponseEntity<ResponseData<PostOrderResponse>> order(@RequestBody @Validated PostOrderRequest postOrderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseData.from(ResponseDataCode.CREATE, orderService.createOrder(postOrderRequest)));
    }
}
