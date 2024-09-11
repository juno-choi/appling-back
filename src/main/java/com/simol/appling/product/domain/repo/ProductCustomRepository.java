package com.simol.appling.product.domain.repo;

import com.simol.appling.product.domain.dto.GetProductListRequest;
import com.simol.appling.product.domain.entity.ProductEntity;
import org.springframework.data.domain.Page;

public interface ProductCustomRepository {
    Page<ProductEntity> findAll(GetProductListRequest getProductListRequest);
}
