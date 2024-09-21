package com.simol.appling.order.domain.dto;


import com.simol.appling.global.api.enums.Sort;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetOrderListRequest {
    private int size;
    private int page;
    private Sort sort;
    private String orderContact;

    public static GetOrderListRequest from(int size, int page, Sort sort, String orderContact) {
        return GetOrderListRequest.builder()
                .size(size)
                .page(page)
                .sort(sort)
                .orderContact(orderContact)
                .build();
    }
}
