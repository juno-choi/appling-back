package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.simol.appling.product.domain.entity.ProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(description = "상품리스트")
public record ProductListResponse(
        @Schema(description = "페이지 마지막 여부", example = "true")
        Boolean last,
        @Schema(description = "페이지 비어있음 여부", example = "false")
        Boolean empty,
        @Schema(description = "총 페이지", example = "1")
        int totalPage,
        @Schema(description = "총 데이터 수", example = "1")
        Long totalElements,
        @Schema(description = "페이지에 존재하는 데이터 수", example = "1")
        int numberOfElement,
        @Schema(description = "리스트")
        List<ProductVo> productList
){
        public static ProductListResponse from(Page<ProductEntity> productPage) {
                int totalPage = productPage.getTotalPages();
                Long totalElements = productPage.getTotalElements();
                int numberOfElement = productPage.getNumberOfElements();
                Boolean last = productPage.isLast();
                Boolean empty = productPage.isEmpty();

                List<ProductVo> productVoList = productPage.stream()
                        .map(ProductVo::from)
                        .toList();

                return ProductListResponse.builder()
                        .productList(productVoList)
                        .totalPage(totalPage)
                        .totalElements(totalElements)
                        .numberOfElement(numberOfElement)
                        .last(last)
                        .empty(empty)
                        .build();
        }
}
