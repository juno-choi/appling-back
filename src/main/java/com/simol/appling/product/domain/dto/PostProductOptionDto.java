package com.simol.appling.product.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simol.appling.product.domain.enums.ProductOptionStatus;
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
@Schema(description = "상품명 옵션 (등록)")
public class PostProductOptionDto {
    @JsonProperty("product_option_name")
    @NotNull(message = "옵션명을 입력해주세요.")
    @Schema(description = "옵션명", example = "11-12과")
    private String productOptionName;
    @JsonProperty("product_option_sort")
    @Schema(description = "옵션 정렬 순서 (미입력시 가장 뒤로 자동 처리)", example = "1")
    private int productOptionSort;
    @JsonProperty("product_option_price")
    @NotNull(message = "옵션 가격을 입력해주세요.")
    @Schema(description = "옵션 가격", example = "100000")
    private int productOptionPrice;
    @JsonProperty("product_option_stock")
    @NotNull(message = "옵션 재고를 입력해주세요.")
    @Schema(description = "옵션 재고", example = "100")
    private int productOptionStock;
    @JsonProperty("product_option_status")
    @NotNull(message = "옵션 상태를 입력해주세요.")
    @Schema(description = "옵션 상태", example = "ON_SALE")
    private ProductOptionStatus productOptionStatus;
    @JsonProperty("product_option_description")
    @NotNull(message = "옵션 설명을 입력해주세요.")
    @Schema(description = "옵션 설명", example = "아리수 11-12과 입니다.")
    private String productOptionDescription;
}
