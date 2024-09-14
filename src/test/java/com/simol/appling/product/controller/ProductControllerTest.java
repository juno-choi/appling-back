package com.simol.appling.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simol.appling.product.domain.dto.PostProductOptionDto;
import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.dto.PutProductOptionDto;
import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.entity.ProductOptionEntity;
import com.simol.appling.product.domain.enums.ProductOptionStatus;
import com.simol.appling.product.domain.enums.ProductStatus;
import com.simol.appling.product.domain.enums.ProductType;
import com.simol.appling.product.domain.repo.ProductOptionRepository;
import com.simol.appling.product.domain.repo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productOptionRepository.deleteAll();
    }

    @Test
    @DisplayName("[POST] /api/v1/product")
    void postProduct() throws Exception {
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
                .productName("등록 상품")
                .productType(ProductType.OPTION)
                .productOption(List.of(option))
                .build();

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)));

        //then
        perform.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("[PUT] /api/v1/product")
    void putProduct() throws Exception {
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
                .productName("등록 상품")
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
                .productType(ProductType.OPTION)
                .productName("수정 상품")
                .productStatus(ProductStatus.ON_SALE)
                .productOption(List.of(putOption))
                .build();

        //when
        ResultActions perform = mockMvc.perform(put("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(putProductRequest)));

        //then
        perform.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[GET] /api/v1/product")
    void getProduct() throws Exception {
        //given
        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("등록 상품")
                .productType(ProductType.OPTION)
                .build();
        ProductEntity saveProduct1 = productRepository.save(ProductEntity.from(productRequest));
        PostProductOptionDto option = PostProductOptionDto.builder()
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .productOptionStock(100)
                .productOptionDescription("아리수 11-12과 입니다.")
                .productOptionSort(1)
                .build();
        ProductOptionEntity saveProductOption = productOptionRepository.save(ProductOptionEntity.from(option, saveProduct1));
        saveProduct1.getProductOptionList().add(saveProductOption);
        productRepository.save(saveProduct1);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v1/product").param("size", "10").param("page", "0").param("sort", "DESC"));

        //then
        perform.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[PUT] /api/v1/product 유효하지 않은 상품은 실패한다.")
    void putProductFail() throws Exception{
        //given
        final Long NOT_EXISTS_PRODUCT_ID = 100L;
        PutProductRequest putProductRequest = PutProductRequest.builder()
                .productId(NOT_EXISTS_PRODUCT_ID)
                .productType(ProductType.OPTION)
                .productName("수정 상품")
                .productStatus(ProductStatus.ON_SALE)
                .build();
        //when
        ResultActions perform = mockMvc.perform(put("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(putProductRequest)));
        //then
        perform.andExpect(status().is4xxClientError());
        perform.andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8).contains("유효하지 않은");
    }

    @Test
    @DisplayName("[GET] /api/v1/product/{productId} 유효하지 않은 상품은 실패한다.")
    void getProductDetailFailByProductId() throws Exception{
        //given
        final Long NOT_EXISTS_PRODUCT_ID = 0L;
        //when
        ResultActions perform = mockMvc.perform(get("/api/v1/product/{productId}", NOT_EXISTS_PRODUCT_ID));
        //then
        perform.andExpect(status().is4xxClientError());
        perform.andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8).contains("유효하지 않은");
    }


    @Test
    @DisplayName("[GET] /api/v1/product/{productId}")
    void getProductDetail() throws Exception{
        //given
        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("등록 상품")
                .productType(ProductType.OPTION)
                .build();
        ProductEntity saveProduct1 = productRepository.save(ProductEntity.from(productRequest));
        PostProductOptionDto option = PostProductOptionDto.builder()
                .productOptionName("11-12과")
                .productOptionPrice(100000)
                .productOptionStatus(ProductOptionStatus.ON_SALE)
                .productOptionStock(100)
                .productOptionDescription("아리수 11-12과 입니다.")
                .productOptionSort(1)
                .build();
        ProductOptionEntity saveProductOption = productOptionRepository.save(ProductOptionEntity.from(option, saveProduct1));
        saveProduct1.getProductOptionList().add(saveProductOption);
        productRepository.save(saveProduct1);
        //when
        ResultActions perform = mockMvc.perform(get("/api/v1/product/{productId}", saveProduct1.getProductId()));
        //then
        perform.andExpect(status().isOk());
    }
}