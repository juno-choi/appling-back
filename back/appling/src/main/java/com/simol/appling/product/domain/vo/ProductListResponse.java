package com.simol.appling.product.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record ProductListResponse(
        Boolean last,
        Boolean empty,
        int totalPage,
        Long totalElements,
        int numberOfElement,
        List<ProductVo> productList
){}
