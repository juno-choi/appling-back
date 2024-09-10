package com.simol.appling.product.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.enums.ProductStatus;
import com.simol.appling.product.domain.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductRequest {

    @NotNull(message = "상품명을 입력해 주세요.")
    @JsonProperty("product_name")
    @Schema(description = "상품명", example = "아리수")
    private String productName;

    @NotNull(message = "상품 타입을 입력해 주세요. ex) OPTION")
    @JsonProperty("product_type")
    @Schema(description = "상품 타입", example = "OPTION")
    private ProductType productType;
    @NotNull(message = "상품 옵션을 입력해주세요.")
    @JsonProperty("product_option")
    @Schema(description = "상품 옵션")
    private List<PostProductOptionDto> productOption;

}
