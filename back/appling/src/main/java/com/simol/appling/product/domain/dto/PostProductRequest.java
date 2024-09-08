package com.simol.appling.product.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductRequest {

    @NotNull(message = "상품명을 입력해 주세요.")
    @JsonProperty("product_name")
    @Schema(description = "상품명", example = "아리수")
    private String productName;
    @NotNull(message = "상품 무게를 입력해 주세요.")
    @JsonProperty("product_weight")
    @Schema(description = "상품 무게", example = "5")
    private int productWeight;
    @NotNull(message = "상품 타입을 입력해 주세요. ex) 사과는 11과")
    @JsonProperty("product_type")
    @Schema(description = "상품 타입", example = "11과")
    private String productType;
    @NotNull(message = "상품 가격을 입력해 주세요.")
    @JsonProperty("product_price")
    @Schema(description = "상품 가격", example = "100000")
    private int productPrice;
    @NotNull(message = "상품 수량을 입력해 주세요.")
    @JsonProperty("product_stock")
    @Schema(description = "상품 수량", example = "100")
    private int productStock;

    public ProductEntity toProductEntity() {
        return ProductEntity.builder()
                .productName(productName)
                .productWeight(productWeight)
                .productType(productType)
                .productPrice(productPrice)
                .productStock(productStock)
                .productStatus(ProductStatus.ON_SALE)
                .build();
    }
}
