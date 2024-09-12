package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.enums.ProductStatus;
import com.simol.appling.product.domain.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(description = "상품 반환 데이터")
public record ProductVo (
    @Schema(description = "상품번호", example = "1")
    Long productId,
    @Schema(description = "상품명", example = "아리수")
    String productName,
    @Schema(description = "상품 타입", example = "OPTION")
    ProductType productType,
    @Schema(description = "상품 상태", example = "ON_SALE")
    ProductStatus productStatus
    ) {
    public static ProductVo from(ProductEntity productEntity) {
        return ProductVo.builder()
            .productId(productEntity.getProductId())
            .productName(productEntity.getProductName())
            .productType(productEntity.getProductType())
            .productStatus(productEntity.getProductStatus())
            .build();
    }
}
