package com.simol.appling.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.order.domain.entity.OrderEntity;
import com.simol.appling.order.domain.entity.OrderProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(description = "주문 상품")
public record OrderProductVo(
        @Schema(description = "주문 상품 번호", example = "1")
        Long orderProductId,
        @Schema(description = "주문 상품 이름", example = "아리수")
        String orderProductName,
        @Schema(description = "주문 상품 옵션명", example = "5kg 11과")
        String orderProductOptionName,
        @Schema(description = "수량", example = "2")
        int quantity,
        @Schema(description = "가격", example = "100000")
        int price
) {
    public static OrderProductVo from(OrderProductEntity productEntity) {
        return OrderProductVo.builder()
                .orderProductId(productEntity.getOrderProductId())
                .orderProductName(productEntity.getOrderProductName())
                .orderProductOptionName(productEntity.getOrderProductOptionName())
                .quantity(productEntity.getQuantity())
                .price(productEntity.getPrice())
                .build();
    }
}
