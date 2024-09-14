package com.simol.appling.order.service;

import com.simol.appling.order.domain.dto.PostOrderRequest;
import com.simol.appling.order.domain.vo.PostOrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public PostOrderResponse createOrder(PostOrderRequest postOrderRequest) {
        return null;
    }
}
