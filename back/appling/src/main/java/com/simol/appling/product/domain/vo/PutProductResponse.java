package com.simol.appling.product.domain.vo;

import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.enums.ProductStatus;
import lombok.Builder;

@Builder
public record PutProductResponse(
    Long productId,
    String productName,
    int productWeight,
    String productType,
    ProductStatus productStatus,
    int productPrice,
    int productStock
) {
    public static PutProductResponse from(ProductEntity updateProductEntity) {
        return PutProductResponse.builder()
                .productId(updateProductEntity.getProductId())
                .productName(updateProductEntity.getProductName())
                .productWeight(updateProductEntity.getProductWeight())
                .productType(updateProductEntity.getProductType())
                .productStatus(updateProductEntity.getProductStatus())
                .productPrice(updateProductEntity.getProductPrice())
                .productStock(updateProductEntity.getProductStock())
                .build();
    }
}
