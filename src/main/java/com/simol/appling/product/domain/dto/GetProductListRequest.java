package com.simol.appling.product.domain.dto;

import com.simol.appling.global.api.enums.Sort;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetProductListRequest {
    private int size;
    private int page;
    private Sort sort;
    private String search;

    public static GetProductListRequest from(int size, int page, Sort sort, String search) {
        return GetProductListRequest.builder()
                .size(size)
                .page(page)
                .sort(sort)
                .search(search)
                .build();
    }
}
