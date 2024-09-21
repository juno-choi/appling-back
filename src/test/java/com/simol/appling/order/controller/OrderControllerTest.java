package com.simol.appling.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simol.appling.order.domain.dto.GetOrderListRequest;
import com.simol.appling.order.domain.dto.PostOrderDto;
import com.simol.appling.order.domain.dto.PostOrderRequest;
import com.simol.appling.order.domain.entity.OrderEntity;
import com.simol.appling.order.domain.repository.OrderRepository;
import com.simol.appling.order.service.OrderServiceImpl;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        productOptionRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("[POST] /api/v1/order")
    void postOrder() throws Exception {
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
        ResultActions perform = mockMvc.perform(post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postOrderRequest)));

        //then
        perform.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("[GET] /api/v1/order")
    void getOrderList() throws Exception {
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

        //when
        ResultActions perform = mockMvc.perform(
                get("/api/v1/order")
                        .param("size", "10")
                        .param("page", "0")
                        .param("sort", "DESC")
                        .param("orderContact", orderContact));
        //then
        perform.andExpect(status().isOk());
        Assertions.assertThat(perform.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)).contains("0000");
    }

}