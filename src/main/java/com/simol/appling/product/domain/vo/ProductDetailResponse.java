package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.product.domain.enums.ProductStatus;
import com.simol.appling.product.domain.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(description = "상품 반환 데이터")
public record ProductDetailResponse(
    @Schema(description = "상품번호", example = "1")
    Long productId,
    @Schema(description = "상품명", example = "아리수")
    String productName,
    @Schema(description = "상품 타입", example = "OPTION")
    ProductType productType,
    @Schema(description = "상품 상태", example = "ON_SALE")
    ProductStatus productStatus,
    @Schema(description = "상품 옵션")
    List<ProductOptionVo> productOption

) {
}
