package com.simol.appling.product.service;

import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.vo.PostProductResponse;

public interface ProductService {
    PostProductResponse createProduct(PostProductRequest postProductRequest);
}
