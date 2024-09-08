package com.simol.appling.product.domain.repo;

import com.simol.appling.product.domain.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {
    Page<ProductEntity> findAll(Pageable pageable, String search);
}
