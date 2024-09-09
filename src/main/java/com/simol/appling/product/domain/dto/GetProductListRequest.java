package com.simol.appling.product.domain.dto;

import com.simol.appling.global.api.enums.Sort;
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
