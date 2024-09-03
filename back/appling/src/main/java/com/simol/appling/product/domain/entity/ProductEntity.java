package com.simol.appling.product.domain.entity;

import com.simol.appling.global.entity.CommonEntity;
import com.simol.appling.product.domain.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ProductEntity extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private int productWeight;
    private String productType;
    private ProductStatus productStatus;
    private int productPrice;
    private int productStock;
}
