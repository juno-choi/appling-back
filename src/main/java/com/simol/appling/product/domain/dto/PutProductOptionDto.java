package com.simol.appling.product.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simol.appling.product.domain.enums.OptionStatus;
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
@Schema(description = "상품명 옵션 (수정)")
public class PutProductOptionDto {
    @JsonProperty("option_id")
    @Schema(description = "옵션 id (비어있을 경우 새롭게 추가)", example = "1")
    private Long optionId;
    @JsonProperty("option_name")
    @NotNull(message = "옵션명을 입력해주세요.")
    @Schema(description = "옵션명", example = "11-12과")
    private String optionName;
    @JsonProperty("option_sort")
    @Schema(description = "옵션 정렬 순서 (미입력시 가장 뒤로 자동 처리)", example = "1")
    private int optionSort;
    @JsonProperty("option_price")
    @NotNull(message = "옵션 가격을 입력해주세요.")
    @Schema(description = "옵션 가격", example = "100000")
    private int optionPrice;
    @JsonProperty("option_stock")
    @NotNull(message = "옵션 재고를 입력해주세요.")
    @Schema(description = "옵션 재고", example = "100")
    private int optionStock;
    @JsonProperty("option_status")
    @NotNull(message = "옵션 상태를 입력해주세요.")
    @Schema(description = "옵션 상태", example = "ON_SALE")
    private OptionStatus optionStatus;
    @JsonProperty("option_description")
    @NotNull(message = "옵션 설명을 입력해주세요.")
    @Schema(description = "옵션 설명", example = "아리수 11-12과 입니다.")
    private String optionDescription;
}
