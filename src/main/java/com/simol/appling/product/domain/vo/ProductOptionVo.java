package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.product.domain.entity.ProductOptionEntity;
import com.simol.appling.product.domain.enums.ProductOptionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(description = "상품 옵션 반환 데이터")
public record ProductOptionVo (
    @Schema(description = "상품 옵션 번호", example = "1")
    Long productOptionId,
    @Schema(description = "상품 옵션명", example = "5kg 11-12과")
    String productOptionName,
    @Schema(description = "상품 정렬 순서", example = "1")
    int productOptionSort,
    @Schema(description = "상품 옵션 가격", example = "100000")
    int productOptionPrice,
    @Schema(description = "상품 옵션 재고", example = "100")
    int productOptionStock,
    @Schema(description = "상품 옵션 상태", example = "ON_SALE")
    ProductOptionStatus productOptionStatus,
    @Schema(description = "상품 옵션 설명", example = "5kg 11-12과입니다.")
    String productOptionDescription
){
    public static List<ProductOptionVo> convertListFrom(List<ProductOptionEntity> productOptionList) {
        return productOptionList.stream()
                .map(ProductOptionVo::from)
                .collect(Collectors.toList());
    }

    public static ProductOptionVo from(ProductOptionEntity productOptionEntity) {
        return ProductOptionVo.builder()
                .productOptionId(productOptionEntity.getProductOptionId())
                .productOptionName(productOptionEntity.getProductOptionName())
                .productOptionSort(productOptionEntity.getProductOptionSort())
                .productOptionPrice(productOptionEntity.getProductOptionPrice())
                .productOptionStock(productOptionEntity.getProductOptionStock())
                .productOptionStatus(productOptionEntity.getProductOptionStatus())
                .productOptionDescription(productOptionEntity.getProductOptionDescription())
                .build();
    }
}
