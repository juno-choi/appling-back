package com.simol.appling.order.service;

import com.simol.appling.order.domain.dto.GetOrderListRequest;
import com.simol.appling.order.domain.dto.PostOrderRequest;
import com.simol.appling.order.domain.vo.OrderResponseList;
import com.simol.appling.order.domain.vo.PostOrderResponse;

public interface OrderService {
    PostOrderResponse createOrder(PostOrderRequest postOrderRequest);

    OrderResponseList getOrderList(GetOrderListRequest getOrderListRequest);
}
