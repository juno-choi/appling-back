package com.simol.appling.product.service;

import com.simol.appling.product.domain.dto.GetProductListRequest;
import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.enums.ProductStatus;
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
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
    }

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

    @Test
    @DisplayName("상풍 번호가 유효하지 않으면 수정에 실패한다.")
    void putProductFail() {
        //given
        PutProductRequest putProductRequest = PutProductRequest.builder()
                .productId(0L)
                .productName("아리수")
                .productWeight(5)
                .productType("11과")
                .productPrice(100_000)
                .productStock(100)
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
        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("아리수")
                .productWeight(5)
                .productType("11과")
                .productPrice(100_000)
                .productStock(100)
                .build();

        ProductEntity saveProduct = productRepository.save(productRequest.toProductEntity());
        PutProductRequest putProductRequest = PutProductRequest.builder()
                .productId(saveProduct.getProductId())
                .productName("아리수")
                .productWeight(5)
                .productType("11과")
                .productPrice(200_000)
                .productStock(100)
                .productStatus(ProductStatus.ON_SALE)
                .build();
        //when
        PutProductResponse putProductResponse = productService.putProduct(putProductRequest);
        //then
        ProductEntity productEntity = productRepository.findById(saveProduct.getProductId()).get();
        Assertions.assertThat(putProductResponse.productPrice()).isEqualTo(200_000);
        Assertions.assertThat(productEntity.getProductPrice()).isEqualTo(200_000);
    }

    @Test
    @DisplayName("상품 리스트를 불러오는데 성공")
    void getProductList() {
        //given
        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("아리수")
                .productWeight(5)
                .productType("11과")
                .productPrice(100_000)
                .productStock(100)
                .build();
        ProductEntity saveProduct = productRepository.save(productRequest.toProductEntity());

        Pageable pageable = Pageable.ofSize(10);
        // pageable.next();
        GetProductListRequest request = GetProductListRequest.builder()
                .pageable(pageable)
                .search("")
                .build();

        //when
        ProductListResponse productList = productService.getProductList(request);

        //then
        Assertions.assertThat(productList.productList().get(0).productName()).isEqualTo(saveProduct.getProductName());
    }
}