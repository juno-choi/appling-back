package com.simol.appling.product.domain.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simol.appling.product.domain.entity.ProductOptionEntity;
import com.simol.appling.product.domain.entity.QProductEntity;
import com.simol.appling.product.domain.entity.QProductOptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductOptionCustomRepositoryImpl implements ProductOptionCustomRepository {
    private final JPAQueryFactory querydsl;

    @Override
    public List<ProductOptionEntity> findAllByProductOptionId(List<Long> idList) {
        QProductOptionEntity productOption = QProductOptionEntity.productOptionEntity;
        QProductEntity product = QProductEntity.productEntity;

        List<ProductOptionEntity> fetch = querydsl.selectFrom(productOption)
                .join(productOption.product, product)
                .fetchJoin()
                .where(productOption.productOptionId.in(idList))
                .fetch();
        return fetch;
    }
}
