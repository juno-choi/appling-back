package com.simol.appling.product.domain.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.entity.QProductEntity;
import com.simol.appling.product.domain.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository{
    private final JPAQueryFactory querydsl;

    @Override
    public Page<ProductEntity> findAll(Pageable pageable, String search) {
        QProductEntity product = QProductEntity.productEntity;

        List<ProductEntity> fetch = querydsl.selectFrom(product)
                .orderBy(product.productId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = querydsl.selectFrom(product).fetch().stream().count();
        return new PageImpl<>(fetch, pageable, total);
    }
}
