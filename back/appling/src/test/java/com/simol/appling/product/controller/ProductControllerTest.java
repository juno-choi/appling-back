package com.simol.appling.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simol.appling.product.domain.dto.PostProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/post/product")
    void postProduct() throws Exception {
        //given
        PostProductRequest productRequest = PostProductRequest.builder()
                .productName("등록 상품")
                .productWeight(5)
                .productPrice(10000)
                .productStock(10)
                .productType("11과")
                .build();


        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)));

        //then
        perform.andExpect(status().isCreated());
    }

}