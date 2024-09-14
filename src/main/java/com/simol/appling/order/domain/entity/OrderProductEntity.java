package com.simol.appling.order.domain.entity;

import com.simol.appling.global.entity.CommonEntity;
import com.simol.appling.product.domain.entity.ProductOptionEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_product")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class OrderProductEntity extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;
    private String orderProductName;
    private String orderProductOptionName;
    private int quantity;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_option_id")
    private ProductOptionEntity productOption;
}
