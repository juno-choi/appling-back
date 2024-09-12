package com.simol.appling.product.service;

import com.simol.appling.global.api.enums.Sort;
import com.simol.appling.product.domain.dto.*;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.entity.ProductOptionEntity;
import com.simol.appling.product.domain.enums.ProductOptionStatus;
import com.simol.appling.product.domain.enums.ProductStatus;
import com.simol.appling.product.domain.enums.ProductType;
import com.simol.appling.product.domain.repo.ProductOptionRepository;
import com.simol.appling.product.domain.repo.ProductRepository;
import com.simol.appling.product.domain.vo.PostProductResponse;
import com.simol.appling.product.domain.vo.ProductListResponse;
import com.simol.appling.product.domain.vo.PutProductResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
        productOptionRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록에 성공한다.")
    void createProduct() {
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

        //when
        PostProductResponse product = productService.createProduct(productRequest);
        //then
        Assertions.assertThat(product.productId()).isNotNull();
    }

    @Test
    @DisplayName("상풍 번호가 유효하지 않으면 수정에 실패한다.")
    void putProductFail() {
        //given
        PutProductRequest putProductRequest = PutProductRequest.builder()
                .productId(0L)
                .productName("아리수")
                .productType(ProductType.OPTION)
                .productStatus(ProductStatus.ON_SALE)
                .build();
        //when
        //then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> productService.putProduct(putProductRequest))
                        .withMessageContaining("유효하지 않은");
    }

    @Test
    @DisplayName("상풍 수정에 성공한다.")
    void putProduct() {
        //given
        final String CHANGE_PRODUCT_NAME = "시나노 골드";
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

        ProductEntity saveProduct = productRepository.save(ProductEntity.from(productRequest));
        ProductOptionEntity saveProductOption = productOptionRepository.save(ProductOptionEntity.from(option, saveProduct));
        saveProduct.getProductOptionList().add(saveProductOption);
        productRepository.save(saveProduct);

        PutProductOptionDto putOption = PutProductOptionDto.builder()
                .productOptionId(saveProductOption.getProductOptionId())
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .productOptionStock(100)
                .productOptionDescription("아리수 11-12과 입니다.")
                .productOptionSort(1)
                .build();

        PutProductRequest putProductRequest = PutProductRequest.builder()
                .productId(saveProduct.getProductId())
                .productName(CHANGE_PRODUCT_NAME)
                .productType(ProductType.OPTION)
                .productStatus(ProductStatus.ON_SALE)
                .productOption(List.of(putOption))
                .build();
        //when
        PutProductResponse putProductResponse = productService.putProduct(putProductRequest);
        //then
        ProductEntity productEntity = productRepository.findById(saveProduct.getProductId()).get();
        Assertions.assertThat(putProductResponse.productName()).isEqualTo(CHANGE_PRODUCT_NAME);
        Assertions.assertThat(productEntity.getProductName()).isEqualTo(CHANGE_PRODUCT_NAME);
    }

    @Test
    @DisplayName("상품 리스트를 불러오는데 성공한다.")
    void getProductList() {
        //given
        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("아리수")
                .productType(ProductType.OPTION)
                .build();
        ProductEntity saveProduct = productRepository.save(ProductEntity.from(productRequest));

        // pageable.next();
        GetProductListRequest request = GetProductListRequest.builder()
                .size(10)
                .page(0)
                .sort(Sort.DESC)
                .search("")
                .build();

        //when
        ProductListResponse productList = productService.getProductList(request);

        //then
        Assertions.assertThat(productList.productList().get(0).productName()).isEqualTo(saveProduct.getProductName());
    }
}