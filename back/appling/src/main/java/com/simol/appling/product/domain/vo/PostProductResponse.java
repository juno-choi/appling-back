package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.product.domain.entity.ProductEntity;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record PostProductResponse(
    Long productId
) {
    public static PostProductResponse createFrom(ProductEntity saveProduct) {
        return PostProductResponse.builder()
                .productId(saveProduct.getProductId())
                .build();
    }
}
