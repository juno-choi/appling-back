package com.simol.appling.product.service;

import com.simol.appling.product.domain.dto.GetProductListRequest;
import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.vo.PostProductResponse;
import com.simol.appling.product.domain.vo.ProductListResponse;
import com.simol.appling.product.domain.vo.PutProductResponse;

public interface ProductService {
    PostProductResponse createProduct(PostProductRequest postProductRequest);
    PutProductResponse putProduct(PutProductRequest putProductRequest);
    ProductListResponse getProductList(GetProductListRequest getProductListRequest);
}
