package com.simol.appling.product.domain.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simol.appling.global.api.enums.Sort;
import com.simol.appling.product.domain.dto.GetProductListRequest;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.entity.QProductEntity;
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
    public Page<ProductEntity> findAll(GetProductListRequest getProductListRequest) {
        Pageable pageable = Pageable.ofSize(getProductListRequest.getSize()).withPage(getProductListRequest.getPage());

        Sort sort = getProductListRequest.getSort();

        QProductEntity product = QProductEntity.productEntity;

        List<ProductEntity> fetch = querydsl.selectFrom(product)
                .orderBy(sort == Sort.ASC ? product.productId.asc() : product.productId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = querydsl.selectFrom(product).fetch().stream().count();
        return new PageImpl<>(fetch, pageable, total);
    }
}
