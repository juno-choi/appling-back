package com.simol.appling.order.domain.entity;

import com.simol.appling.global.entity.CommonEntity;
import com.simol.appling.order.domain.dto.PostOrderRequest;
import com.simol.appling.order.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class OrderEntity extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String orderName;
    private String orderContact;
    private String orderAddress;
    private String orderAddressDetail;
    private String orderZipcode;
    private String recipientName;
    private String recipientContact;
    private String recipientAddress;
    private String recipientAddressDetail;
    private String recipientZipcode;
    private int orderAmount;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductEntity> orderProductList;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDeliveryEntity> orderDeliveryEntityList;

    public static OrderEntity from(PostOrderRequest postOrderRequest) {
        String orderContact = postOrderRequest.getOrderContact().replace("-", "");
        String recipientContact = postOrderRequest.getRecipientContact().replace("-", "");
        return OrderEntity.builder()
            .orderStatus(OrderStatus.TEMP)
            .orderName(postOrderRequest.getOrderName())
            .orderContact(orderContact)
            .orderAddress(postOrderRequest.getOrderAddress())
            .orderAddressDetail(postOrderRequest.getOrderAddressDetail())
            .orderZipcode(postOrderRequest.getOrderZipcode())
            .recipientName(postOrderRequest.getRecipientName())
            .recipientContact(recipientContact)
            .recipientAddress(postOrderRequest.getRecipientAddress())
            .recipientAddressDetail(postOrderRequest.getRecipientAddressDetail())
            .recipientZipcode(postOrderRequest.getRecipientZipcode())
            .build();
    }

    public void updateOrderProductList(List<OrderProductEntity> orderProductEntityList) {
        this.orderProductList = orderProductEntityList;
    }

    public void calculatorTotalAmount(List<OrderProductEntity> orderProductEntityList) {
        int totalAmount = 0;
        for (OrderProductEntity orderProductEntity : orderProductEntityList) {
            totalAmount += orderProductEntity.getPrice() * orderProductEntity.getQuantity();
        }
        this.orderAmount = totalAmount;
    }
}
