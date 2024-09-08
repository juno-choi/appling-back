package com.simol.appling.product.service;

import com.simol.appling.product.domain.dto.GetProductListRequest;
import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.vo.PostProductResponse;
import com.simol.appling.product.domain.vo.ProductListResponse;
import com.simol.appling.product.domain.vo.PutProductResponse;

public interface ProductService {
    /**
     * 상품 등록
     * @param postProductRequest
     * @return
     */
    PostProductResponse createProduct(PostProductRequest postProductRequest);

    /**
     * 상품 수정
     * @param putProductRequest
     * @return
     */
    PutProductResponse putProduct(PutProductRequest putProductRequest);

    /**
     * 상품 리스트 불러오기
     * @param getProductListRequest
     * @return
     */
    ProductListResponse getProductList(GetProductListRequest getProductListRequest);
}
