package com.simol.appling.order.service;

import com.simol.appling.order.domain.dto.GetOrderListRequest;
import com.simol.appling.order.domain.dto.PostOrderDto;
import com.simol.appling.order.domain.dto.PostOrderRequest;
import com.simol.appling.order.domain.entity.OrderEntity;
import com.simol.appling.order.domain.entity.OrderProductEntity;
import com.simol.appling.order.domain.enums.OrderStatus;
import com.simol.appling.order.domain.repository.OrderRepository;
import com.simol.appling.order.domain.vo.OrderListResponse;
import com.simol.appling.order.domain.vo.OrderResponse;
import com.simol.appling.order.domain.vo.PostOrderResponse;
import com.simol.appling.product.domain.dto.PostProductOptionDto;
import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.entity.ProductOptionEntity;
import com.simol.appling.product.domain.enums.ProductOptionStatus;
import com.simol.appling.product.domain.enums.ProductType;
import com.simol.appling.product.domain.repo.ProductOptionRepository;
import com.simol.appling.product.domain.repo.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        productOptionRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("존재하지 않는 상품을 주문시 주문에 실패한다.")
    void createOrderFailByNonExistProduct() {
        //given
        PostOrderDto postOrderDto = PostOrderDto.builder()
                .productOptionId(1L)
                .quantity(1)
                .build();

        PostOrderRequest postOrderRequest = PostOrderRequest.builder()
                .orderProductList(List.of(postOrderDto))
                .build();

        //when
        //then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> orderService.createOrder(postOrderRequest))
                        .withMessageContaining("유효하지 않은 상품");
    }

    @Test
    @DisplayName("주문에 성공한다.")
    void createOrder() {
        //given
        PostProductOptionDto option = PostProductOptionDto.builder()
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .productOptionStock(100)
                .productOptionDescription("아리수 11-12과 입니다.")
                .productOptionSort(1)
                .build();

        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("아리수")
                .productType(ProductType.OPTION)
                .productOption(List.of(option))
                .build();

        ProductEntity productEntity = ProductEntity.from(productRequest);
        ProductOptionEntity productOptionEntity = ProductOptionEntity.from(option, productEntity);
        productEntity.getProductOptionList().add(productOptionEntity);
        ProductEntity saveProduct = productRepository.save(productEntity);

        PostOrderDto postOrderDto = PostOrderDto.builder()
                .productOptionId(saveProduct.getProductOptionList().get(0).getProductOptionId())
                .quantity(1)
                .build();

        PostOrderRequest postOrderRequest = PostOrderRequest.builder()
                .orderProductList(List.of(postOrderDto))
                .orderName("주문자")
                .orderContact("010-1234-5678")
                .orderAddress("경기도 성남시 분당구 판교역로 231")
                .orderAddressDetail("H스퀘어 S동 5층")
                .orderZipcode("12345")
                .recipientName("받는이")
                .recipientContact("010-1234-5678")
                .recipientAddress("경기도 성남시 분당구 판교역로 231")
                .recipientAddressDetail("H스퀘어 S동 6층")
                .recipientZipcode("12345")
                .build();

        //when
        PostOrderResponse order = orderService.createOrder(postOrderRequest);
        //then
        OrderEntity orderEntity = orderRepository.findById(order.orderId()).get();

        Assertions.assertThat(orderEntity.getOrderName()).isEqualTo(postOrderRequest.getOrderName());
    }

    @Test
    @DisplayName("주문을 조회하는데 성공한다.")
    void getOrderList() {
        //given
        final String orderContact = "01012345678";

        PostProductOptionDto option = PostProductOptionDto.builder()
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .productOptionStock(100)
                .productOptionDescription("아리수 11-12과 입니다.")
                .productOptionSort(1)
                .build();

        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("아리수")
                .productType(ProductType.OPTION)
                .productOption(List.of(option))
                .build();

        ProductEntity productEntity = ProductEntity.from(productRequest);
        ProductOptionEntity productOptionEntity = ProductOptionEntity.from(option, productEntity);
        productEntity.getProductOptionList().add(productOptionEntity);
        ProductEntity saveProduct = productRepository.save(productEntity);

        PostOrderDto postOrderDto = PostOrderDto.builder()
                .productOptionId(saveProduct.getProductOptionList().get(0).getProductOptionId())
                .quantity(1)
                .build();

        PostOrderRequest postOrderRequest = PostOrderRequest.builder()
                .orderProductList(List.of(postOrderDto))
                .orderName("주문자")
                .orderContact(orderContact)
                .orderAddress("경기도 성남시 분당구 판교역로 231")
                .orderAddressDetail("H스퀘어 S동 5층")
                .orderZipcode("12345")
                .recipientName("받는이")
                .recipientContact("010-1234-5678")
                .recipientAddress("경기도 성남시 분당구 판교역로 231")
                .recipientAddressDetail("H스퀘어 S동 6층")
                .recipientZipcode("12345")
                .build();

        for (int i=0; i<30; i++) {
            orderRepository.save(OrderEntity.from(postOrderRequest));
        }

        GetOrderListRequest getOrderListRequest = GetOrderListRequest.builder()
                .size(10)
                .page(0)
                .orderContact(orderContact)
                .build();
        //when
        OrderListResponse orderList = orderService.getOrderList(getOrderListRequest);
        //then
        Assertions.assertThat(orderList.orderList().size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("주문을 상세 성공한다.")
    void getOrder() {
        //given
        final String orderContact = "01012345678";

        PostProductOptionDto option = PostProductOptionDto.builder()
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .productOptionStock(100)
                .productOptionDescription("아리수 11-12과 입니다.")
                .productOptionSort(1)
                .build();

        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("아리수")
                .productType(ProductType.OPTION)
                .productOption(List.of(option))
                .build();

        ProductEntity productEntity = ProductEntity.from(productRequest);
        ProductOptionEntity productOptionEntity = ProductOptionEntity.from(option, productEntity);
        productEntity.getProductOptionList().add(productOptionEntity);
        ProductEntity saveProduct = productRepository.save(productEntity);

        PostOrderDto postOrderDto = PostOrderDto.builder()
                .productOptionId(saveProduct.getProductOptionList().get(0).getProductOptionId())
                .quantity(1)
                .build();

        PostOrderRequest postOrderRequest = PostOrderRequest.builder()
                .orderProductList(List.of(postOrderDto))
                .orderName("주문자")
                .orderContact(orderContact)
                .orderAddress("경기도 성남시 분당구 판교역로 231")
                .orderAddressDetail("H스퀘어 S동 5층")
                .orderZipcode("12345")
                .recipientName("받는이")
                .recipientContact("010-1234-5678")
                .recipientAddress("경기도 성남시 분당구 판교역로 231")
                .recipientAddressDetail("H스퀘어 S동 6층")
                .recipientZipcode("12345")
                .build();

        OrderEntity orderEntity = OrderEntity.from(postOrderRequest);
        List<OrderProductEntity> orderProductList = Collections.singletonList(OrderProductEntity.from(postOrderDto, orderEntity, productOptionEntity));
        orderEntity.updateOrderProductList(orderProductList);
        orderEntity.calculatorTotalAmount(orderProductList);
        OrderEntity saveOrder = orderRepository.save(orderEntity);

        //when
        OrderResponse order = orderService.getOrder(saveOrder.getOrderId());

        //then
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.orderAmount()).isGreaterThan(0);
        Assertions.assertThat(order.orderStatus()).isEqualTo(OrderStatus.TEMP);
    }

}