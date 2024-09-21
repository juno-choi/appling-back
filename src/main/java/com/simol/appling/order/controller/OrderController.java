package com.simol.appling.order.controller;

import com.simol.appling.global.api.annotation.ApiController;
import com.simol.appling.global.api.enums.ResponseDataCode;
import com.simol.appling.global.api.enums.Sort;
import com.simol.appling.global.api.response.ResponseData;
import com.simol.appling.order.domain.dto.GetOrderListRequest;
import com.simol.appling.order.domain.dto.PostOrderRequest;
import com.simol.appling.order.domain.vo.OrderResponseList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/order")
    @Operation(summary = "주문리스트", description = "주문리스트 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PostProductResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    public ResponseEntity<ResponseData<OrderResponseList>> getOrderList(
            @Schema(description = "페이지 크기", defaultValue = "10", nullable = true) @RequestParam(name = "size", required = false, defaultValue = "10" ) int size,
            @Schema(description = "페이지 번호", defaultValue = "0", nullable = true) @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @Schema(description = "페이지 정렬(order id 기준)", defaultValue = "DESC", nullable = true) @RequestParam(name = "sort",required = false, defaultValue = "DESC" ) Sort sort,
            @Schema(description = "사용자 연락처", defaultValue = "") @RequestParam(name = "orderContact") String orderContact) {
        OrderResponseList orderResponseList = orderService.getOrderList(GetOrderListRequest.from(size, page, sort, orderContact));
        return ResponseEntity.ok(ResponseData.from(ResponseDataCode.SUCCESS, orderResponseList));
    }
}
