package com.simol.appling.order.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simol.appling.global.api.enums.Sort;
import com.simol.appling.order.domain.dto.GetOrderListRequest;
import com.simol.appling.order.domain.entity.OrderEntity;
import com.simol.appling.order.domain.entity.QOrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {
    private final JPAQueryFactory querydsl;

    @Override
    public Page<OrderEntity> getOrderList(GetOrderListRequest getOrderListRequest) {
        String orderContact = getOrderListRequest.getOrderContact().replace("-", "");
        Pageable pageable = Pageable.ofSize(getOrderListRequest.getSize()).withPage(getOrderListRequest.getPage());

        Sort sort = getOrderListRequest.getSort();
        QOrderEntity order = QOrderEntity.orderEntity;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(order.orderContact.eq(orderContact));

        List<OrderEntity> fetch = querydsl.selectFrom(order)
                .where(builder)
                .orderBy(sort == Sort.ASC ? order.orderId.asc() : order.orderId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = querydsl.selectFrom(order)
                .where(builder)
                .fetch()
                .stream().count();

        return new PageImpl<>(fetch, pageable, total);
    }
}
