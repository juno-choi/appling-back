package com.simol.appling.order.domain.entity;

import com.simol.appling.global.entity.CommonEntity;
import com.simol.appling.order.domain.enums.OrderDeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_delivery")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class OrderDeliveryEntity extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDeliveryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Enumerated(EnumType.STRING)
    private OrderDeliveryStatus orderDeliveryStatus;

    private String invoice;
}
