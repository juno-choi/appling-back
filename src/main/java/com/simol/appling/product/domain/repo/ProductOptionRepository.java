package com.simol.appling.product.domain.repo;

import com.simol.appling.product.domain.entity.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOptionEntity, Long> {
}
