package com.simol.appling.product.domain.repo;

import com.simol.appling.product.domain.entity.ProductOptionEntity;

import java.util.List;

public interface ProductOptionCustomRepository {
    List<ProductOptionEntity> findAllByProductOptionId(List<Long> idList);
}
