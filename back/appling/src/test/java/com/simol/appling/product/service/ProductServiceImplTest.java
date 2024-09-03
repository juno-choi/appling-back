package com.simol.appling.product.service;

import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.vo.PostProductResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 등록에 성공한다.")
    void createProduct() {
        //given
        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("아리수")
                .productWeight(5)
                .productType("11과")
                .productPrice(100_000)
                .productStock(100)
                .build();
        //when
        PostProductResponse product = productService.createProduct(productRequest);
        //then
        Assertions.assertThat(product.productId()).isNotNull();
    }

}