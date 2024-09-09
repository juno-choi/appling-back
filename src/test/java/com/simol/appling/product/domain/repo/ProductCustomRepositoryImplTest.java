package com.simol.appling.product.domain.repo;

import com.simol.appling.global.api.enums.Sort;
import com.simol.appling.product.domain.dto.GetProductListRequest;
import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.enums.ProductType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class ProductCustomRepositoryImplTest {
    @Autowired
    private ProductCustomRepository productCustomRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("findAll asc 성공")
    void findAllASC() {
        //given
        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("등록 상품")
                .productType(ProductType.OPTION)
                .build();

        ProductEntity saveProduct1 = productRepository.save(productRequest.toProductEntity());
        ProductEntity saveProduct2 = productRepository.save(productRequest.toProductEntity());

        //when
        Page<ProductEntity> productPage = productCustomRepository.findAll(GetProductListRequest.from(10, 0, Sort.ASC, ""));

        //then
        Assertions.assertThat(productPage.getContent().get(0).getProductId()).isEqualTo(saveProduct1.getProductId());
    }

    @Test
    @DisplayName("findAll desc 성공")
    void findAllDESC() {
        //given
        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("등록 상품")
                .productType(ProductType.OPTION)
                .build();

        ProductEntity saveProduct1 = productRepository.save(productRequest.toProductEntity());
        ProductEntity saveProduct2 = productRepository.save(productRequest.toProductEntity());

        //when
        Page<ProductEntity> productPage = productCustomRepository.findAll(GetProductListRequest.from(10, 0, Sort.DESC, ""));

        //then
        Assertions.assertThat(productPage.getContent().get(0).getProductId()).isEqualTo(saveProduct2.getProductId());
    }

}