package com.simol.appling.order.domain.repository;

import com.simol.appling.order.domain.dto.GetOrderListRequest;
import com.simol.appling.order.domain.entity.OrderEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OrderCustomRepository {
    Page<OrderEntity> getOrderList(GetOrderListRequest getOrderListRequest);

    Optional<OrderEntity> getOrder(Long orderId);
}
