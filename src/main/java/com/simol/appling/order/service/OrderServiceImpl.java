package com.simol.appling.order.service;

import com.simol.appling.order.domain.dto.PostOrderDto;
import com.simol.appling.order.domain.dto.PostOrderRequest;
import com.simol.appling.order.domain.entity.OrderEntity;
import com.simol.appling.order.domain.entity.OrderProductEntity;
import com.simol.appling.order.domain.repository.OrderRepository;
import com.simol.appling.order.domain.vo.PostOrderResponse;
import com.simol.appling.product.domain.entity.ProductOptionEntity;
import com.simol.appling.product.domain.repo.ProductOptionRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductOptionRepository productOptionRepository;
    
    @Transactional
    @Override
    public PostOrderResponse createOrder(PostOrderRequest postOrderRequest) {
        // order 데이터 생성
        OrderEntity order = OrderEntity.from(postOrderRequest);

        // orderProduct 데이터 생성
        List<OrderProductEntity> orderProductEntityList = getOrderProductEntityList(postOrderRequest, order);
        // order product 추가 및 총 비용 계산
        order.calculatorTotalAmount(orderProductEntityList);
        order.updateOrderProductList(orderProductEntityList);

        OrderEntity saveOrderEntity = orderRepository.save(order);

        return PostOrderResponse.from(saveOrderEntity);
    }

    /**
     * orderProductEntityList 생성
     * @param postOrderRequest
     * @param order
     * @return
     */
    private List<OrderProductEntity> getOrderProductEntityList(PostOrderRequest postOrderRequest, OrderEntity order) {
        List<OrderProductEntity> orderProductEntityList = new ArrayList<>();
        List<PostOrderDto> orderProductList = postOrderRequest.getOrderProductList();
        for (PostOrderDto dto : orderProductList) {
            ProductOptionEntity productOptionEntity = productOptionRepository.findById(dto.getProductOptionId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

            OrderProductEntity orderProductEntity = OrderProductEntity.from(dto, order, productOptionEntity);
            orderProductEntityList.add(orderProductEntity);
        }
        return orderProductEntityList;
    }
}
