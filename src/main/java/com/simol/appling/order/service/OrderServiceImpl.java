package com.simol.appling.order.service;

import com.simol.appling.order.domain.dto.GetOrderListRequest;
import com.simol.appling.order.domain.dto.PostOrderDto;
import com.simol.appling.order.domain.dto.PostOrderRequest;
import com.simol.appling.order.domain.entity.OrderEntity;
import com.simol.appling.order.domain.entity.OrderProductEntity;
import com.simol.appling.order.domain.repository.OrderCustomRepository;
import com.simol.appling.order.domain.repository.OrderRepository;
import com.simol.appling.order.domain.vo.OrderListResponse;
import com.simol.appling.order.domain.vo.OrderResponse;
import com.simol.appling.order.domain.vo.PostOrderResponse;
import com.simol.appling.product.domain.entity.ProductOptionEntity;
import com.simol.appling.product.domain.repo.ProductOptionCustomRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductOptionCustomRepository productOptionCustomRepository;
    private final OrderCustomRepository orderCustomRepository;
    
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

        List<Long> productOptionIdList = orderProductList.stream().mapToLong(PostOrderDto::getProductOptionId).boxed().toList();

        List<ProductOptionEntity> productOptionEntityList = productOptionCustomRepository.findAllByProductOptionId(productOptionIdList);

        checkOrderValidation(productOptionIdList, productOptionEntityList);

        // orderProductList, productOptionEntityList 모두 optionId 기준으로 정렬하기
        Comparator<PostOrderDto> orderDtoComparator = Comparator.comparing(o -> o.getProductOptionId());
        orderProductList = orderProductList.stream()
                .sorted(orderDtoComparator)
                .collect(Collectors.toList());

        Comparator<ProductOptionEntity> productOptionEntityComparator = Comparator.comparing(o -> o.getProductOptionId());
        productOptionEntityList = productOptionEntityList.stream()
                .sorted(productOptionEntityComparator)
                .collect(Collectors.toList());

        for (int i=0; i<orderProductList.size(); i++) {
            PostOrderDto dto = orderProductList.get(i);
            ProductOptionEntity productOption = productOptionEntityList.get(i);
            OrderProductEntity orderProductEntity = OrderProductEntity.from(dto, order, productOption);
            orderProductEntityList.add(orderProductEntity);
        }

        return orderProductEntityList;
    }

    private void checkOrderValidation(List<Long> productOptionIdList, List<ProductOptionEntity> productOptionEntityList) {
        if (productOptionIdList.size() != productOptionEntityList.size()) {
            throw new IllegalArgumentException("유효하지 않은 상품이 포함되어 있습니다.");
        }
    }

    @Override
    public OrderListResponse getOrderList(GetOrderListRequest getOrderListRequest) {
        // 요청 받은 값으로 주문 리스트를 가져오기
        Page<OrderEntity> orderList = orderCustomRepository.getOrderListByOrderContact(getOrderListRequest);
        return OrderListResponse.from(orderList);
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        OrderEntity orderEntity = orderCustomRepository.getOrder(orderId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 주문번호 입니다."));
        return OrderResponse.from(orderEntity);
    }
}