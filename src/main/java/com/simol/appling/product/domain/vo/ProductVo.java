package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.enums.ProductStatus;
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
    @Schema(description = "상품 무게", example = "5")
    int productWeight,
    @Schema(description = "상품 타입", example = "11과")
    String productType,
    @Schema(description = "상품 상태", example = "ON_SALE")
    ProductStatus productStatus,
    @Schema(description = "상품 가격", example = "100000")
    int productPrice,
    @Schema(description = "상품 수량", example = "100")
    int productStock
    ) {
    public static ProductVo from(ProductEntity productEntity) {
        return ProductVo.builder()
            .productId(productEntity.getProductId())
            .productName(productEntity.getProductName())
            .productWeight(productEntity.getProductWeight())
            .productType(productEntity.getProductType())
            .productStatus(productEntity.getProductStatus())
            .productPrice(productEntity.getProductPrice())
            .productStock(productEntity.getProductStock())
            .build();
    }
}
