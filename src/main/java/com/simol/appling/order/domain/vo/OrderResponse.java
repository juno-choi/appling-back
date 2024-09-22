package com.simol.appling.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.order.domain.entity.OrderEntity;
import com.simol.appling.order.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(description = "주문 반환 데이터")
public record OrderResponse(
        @Schema(description = "주문번호", example = "1")
        Long orderId,
        @Schema(description = "주문상태", example = "COMPLETE")
        OrderStatus orderStatus,
        @Schema(description = "주문자", example = "주문자")
        String orderName,
        @Schema(description = "주문자 전화번호", example = "010-1234-5678")
        String orderContact,
        @Schema(description = "주문자 주소", example = "주소")
        String orderAddress,
        @Schema(description = "주문자 상세 주소", example = "상세 주소")
        String orderAddressDetail,
        @Schema(description = "주문자 우편번호", example = "12345")
        String orderZipcode,
        @Schema(description = "수령인", example = "수령인")
        String recipientName,
        @Schema(description = "수령인 전화번호", example = "010-1234-5678")
        String recipientContact,
        @Schema(description = "수령인 주소", example = "주소")
        String recipientAddress,
        @Schema(description = "수령인 상세 주소", example = "상세 주소")
        String recipientAddressDetail,
        @Schema(description = "수령인 우편번호", example = "12345")
        String recipientZipcode,
        @Schema(description = "주문금액", example = "100000")
        int orderAmount,
        @Schema(description = "주문 상품")
        List<OrderProductVo> orderProductList
) {
        public static OrderResponse from(OrderEntity order) {
                List<OrderProductVo> orderProductVoList = order.getOrderProductList().stream()
                        .map(OrderProductVo::from)
                        .toList();

                return OrderResponse.builder()
                        .orderId(order.getOrderId())
                        .orderStatus(order.getOrderStatus())
                        .orderName(order.getOrderName())
                        .orderContact(order.getOrderContact())
                        .orderAddress(order.getOrderAddress())
                        .orderAddressDetail(order.getOrderAddressDetail())
                        .orderZipcode(order.getOrderZipcode())
                        .recipientName(order.getRecipientName())
                        .recipientContact(order.getRecipientContact())
                        .recipientAddress(order.getRecipientAddress())
                        .recipientAddressDetail(order.getRecipientAddressDetail())
                        .recipientZipcode(order.getRecipientZipcode())
                        .orderAmount(order.getOrderAmount())
                        .orderProductList(orderProductVoList)
                        .build();
        }
}

