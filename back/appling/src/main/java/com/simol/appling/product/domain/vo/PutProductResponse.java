package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "상품 수정 반환 데이터")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PutProductResponse(
    @Schema(description = "상품번호", example = "1")
    Long productId,
    @Schema(description = "상품명", example = "시나노 골드")
    String productName,
    @Schema(description = "상품 무게", example = "10")
    int productWeight,
    @Schema(description = "상품 타입", example = "13과")
    String productType,
    @Schema(description = "상품 상태", example = "SOLD_OUT")
    ProductStatus productStatus,
    @Schema(description = "상품 가격", example = "150000")
    int productPrice,
    @Schema(description = "상품 수량", example = "0")
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
