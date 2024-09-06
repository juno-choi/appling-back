package com.simol.appling.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductListRequest {
    private Pageable pageable;
    private String search;
}
