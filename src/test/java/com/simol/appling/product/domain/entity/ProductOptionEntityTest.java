package com.simol.appling.product.domain.entity;

import com.simol.appling.product.domain.enums.ProductOptionStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


class ProductOptionEntityTest {

    @Test
    @DisplayName("product option id 값이 존재하지 않으면 실패한다.")
    void updateFailByProductOptionId() {
        //given
        ProductOptionEntity originProductOptionEntity = ProductOptionEntity.builder()
                .productOptionId(1L)
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStock(100)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .build();

        // 기존 productOptionId 값은 1인데 2로 요청된 경우임
        ProductOptionEntity requestProductOptionEntity = ProductOptionEntity.builder()
                .productOptionId(2L)
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStock(100)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .build();

        //when
        //then
        Assertions.assertThatExceptionOfType(RuntimeException.class)
                        .isThrownBy(() -> requestProductOptionEntity.updateCreateAt(List.of(originProductOptionEntity)))
                .withMessageContaining("해당 상품에 존재하지 않는 옵션 id가 입력되었습니다.");
    }

    @Test
    @DisplayName("product option 수정 성공")
    void updateSuccess() {
        //given
        ProductOptionEntity originProductOptionEntity = ProductOptionEntity.builder()
                .productOptionId(1L)
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStock(100)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .build();

        // 기존 productOptionId 값은 1인데 2로 요청된 경우임
        ProductOptionEntity requestProductOptionEntity = ProductOptionEntity.builder()
                .productOptionId(1L)
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStock(100)
                .productOptionStatus(ProductOptionStatus.SOLD_OUT)
                .build();

        //when
        requestProductOptionEntity.updateCreateAt(List.of(originProductOptionEntity));
        //then
        Assertions.assertThat(requestProductOptionEntity.getProductOptionStatus()).isEqualTo(ProductOptionStatus.SOLD_OUT);
    }
}