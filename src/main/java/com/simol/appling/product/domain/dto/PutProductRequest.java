package com.simol.appling.product.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PutProductRequest {
    @JsonProperty("product_id")
    @NotNull(message = "상품 번호를 입력해 주세요.")
    @Schema(description = "상품 번호", example = "1")
    private Long productId;
    @NotNull(message = "상품명을 입력해 주세요.")
    @JsonProperty("product_name")
    @Schema(description = "상품명", example = "시나노 골드")
    private String productName;
    @NotNull(message = "상품 무게를 입력해 주세요.")
    @JsonProperty("product_weight")
    @Schema(description = "상품 무게", example = "10")
    private int productWeight;
    @NotNull(message = "상품 타입을 입력해 주세요. ex) 사과는 11과")
    @JsonProperty("product_type")
    @Schema(description = "상품 타입", example = "13과")
    private String productType;
    @NotNull(message = "상품 가격을 입력해 주세요.")
    @JsonProperty("product_price")
    @Schema(description = "상품 가격", example = "150000")
    private int productPrice;
    @NotNull(message = "상품 수량을 입력해 주세요.")
    @JsonProperty("product_stock")
    @Schema(description = "상품 수량", example = "0")
    private int productStock;
    @NotNull(message = "상품 상태를 입력해 주세요.")
    @JsonProperty("product_status")
    @Schema(description = "상품 상태", example = "SOLD_OUT")
    private ProductStatus productStatus;

}
