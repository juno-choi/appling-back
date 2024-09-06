package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.enums.ProductStatus;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record ProductVo (
    Long productId,
    String productName,
    int productWeight,
    String productType,
    ProductStatus productStatus,
    int productPrice,
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
