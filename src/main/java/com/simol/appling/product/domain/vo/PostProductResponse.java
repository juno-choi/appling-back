package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.product.domain.entity.ProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(description = "상품 등록 반환 데이터")
public record PostProductResponse(
    @Schema(description = "상품번호", example = "1") Long productId
) {
    public static PostProductResponse createFrom(ProductEntity saveProduct) {
        return PostProductResponse.builder()
                .productId(saveProduct.getProductId())
                .build();
    }
}
