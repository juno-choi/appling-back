package com.simol.appling.product.domain.entity;

import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.enums.ProductStatus;
import com.simol.appling.product.domain.enums.ProductType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTest {

    @Test
    @DisplayName("상품 업데이트 성공")
    void update() {
        //given
        ProductEntity productEntity = ProductEntity.builder()
                .productId(1L)
                .productName("아리수")
                .productType(ProductType.OPTION)
                .productStatus(ProductStatus.ON_SALE)
                .build();

        PutProductRequest putProductRequest = PutProductRequest.builder()
                .productId(1L)
                .productName("아리수")
                .productType(ProductType.OPTION)
                .productStatus(ProductStatus.SOLD_OUT)
                .build();

        //when
        productEntity.update(putProductRequest);

        //then
        Assertions.assertThat(productEntity.getProductStatus()).isEqualTo(ProductStatus.SOLD_OUT);
    }
}