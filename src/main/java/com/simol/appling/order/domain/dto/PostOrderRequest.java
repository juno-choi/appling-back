package com.simol.appling.order.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostOrderRequest {
    @NotNull(message = "주문 상품 옵션 id를 입력해주세요.")
    @JsonProperty("product_option_id")
    @Schema(description = "상품 옵션 번호", example = "1")
    private Long productOptionId;

    @NotNull(message = "주문 상품 개수를 입력해주세요.")
    @JsonProperty("quantity")
    @Schema(description = "주문 상품 개수", example = "2")
    private int quantity;

    @NotNull(message = "주문 상품 개수를 입력해주세요.")
    @JsonProperty("quantity")
    @Schema(description = "주문자 이름", example = "주문자")
    private String orderName;

    @NotNull
    @JsonProperty("order_contact")
    @Schema(description = "주문자 연락처", example = "010-1234-5678")
    private String orderContact;

    @NotNull
    @JsonProperty("order_address")
    @Schema(description = "주문자 주소", example = "경기도 성남시 분당구 판교역로 231")
    private String orderAddress;

    @NotNull
    @JsonProperty("order_address_detail")
    @Schema(description = "주문자 상세 주소", example = "H스퀘어 S동 5층")
    private String orderAddressDetail;

    @NotNull
    @JsonProperty("order_zipcode")
    @Schema(description = "주문자 우편주소", example = "12345")
    private String orderZipcode;

    @NotNull
    @JsonProperty("recipient_name")
    @Schema(description = "받는사람 이름", example = "받는이")
    private String recipientName;

    @NotNull
    @JsonProperty("recipient_contact")
    @Schema(description = "받는사람 연락처", example = "010-1234-5678")
    private String recipientContact;

    @NotNull
    @JsonProperty("recipient_address")
    @Schema(description = "받는사람 주소", example = "경기도 성남시 분당구 판교역로 231")
    private String recipientAddress;

    @NotNull
    @JsonProperty("recipient_address_detail")
    @Schema(description = "받는사람 상세 주소", example = "H스퀘어 S동 6층")
    private String recipientAddressDetail;

    @NotNull
    @JsonProperty("recipient_zipcode")
    @Schema(description = "받는사람 우편주소", example = "12345")
    private String recipientZipcode;
}
