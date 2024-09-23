package com.simol.appling.product.domain.entity;

import com.simol.appling.product.domain.dto.PutProductOptionDto;
import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.enums.ProductOptionStatus;
import com.simol.appling.product.domain.enums.ProductStatus;
import com.simol.appling.product.domain.enums.ProductType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ProductEntityTest {

    @Test
    @DisplayName("상품 업데이트 성공한다.")
    void update() {
        //given
        ProductEntity productEntity = ProductEntity.builder()
                .productId(1L)
                .productName("아리수")
                .productType(ProductType.OPTION)
                .productStatus(ProductStatus.ON_SALE)
                .productOptionList(new ArrayList<>())
                .build();

        ProductOptionEntity productOptionEntity = ProductOptionEntity.builder()
                .product(productEntity)
                .productOptionPrice(100000)
                .productOptionName("11-12과")
                .productOptionStock(100)
                .productOptionDescription("아리수 11-12과 입니다.")
                .productOptionSort(1)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .build();

        PutProductOptionDto option = PutProductOptionDto.builder()
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .productOptionStock(100)
                .productOptionDescription("아리수 11-12과 입니다.")
                .productOptionSort(1)
                .build();

        PutProductRequest putProductRequest = PutProductRequest.builder()
                .productId(1L)
                .productName("아리수")
                .productType(ProductType.OPTION)
                .productStatus(ProductStatus.SOLD_OUT)
                .productOption(List.of(option))
                .build();

        //when
        productEntity.update(putProductRequest);

        //then
        Assertions.assertThat(productEntity.getProductStatus()).isEqualTo(ProductStatus.SOLD_OUT);
    }
}