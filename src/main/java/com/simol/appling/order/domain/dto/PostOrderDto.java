package com.simol.appling.order.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostOrderDto {
    // todo 주문 상품을 dto로 리스트로 받아야 함.
    @NotNull(message = "주문 상품 옵션 id를 입력해주세요.")
    @JsonProperty("product_option_id")
    @Schema(description = "상품 옵션 번호", example = "1")
    private Long productOptionId;

    @NotNull(message = "주문 상품 개수를 입력해주세요.")
    @JsonProperty("quantity")
    @Schema(description = "주문 상품 개수", example = "2")
    private int quantity;
}
