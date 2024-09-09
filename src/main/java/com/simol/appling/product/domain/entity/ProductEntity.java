package com.simol.appling.product.domain.entity;

import com.simol.appling.global.entity.CommonEntity;
import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.enums.ProductStatus;
import com.simol.appling.product.domain.enums.ProductType;
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

    @Enumerated(EnumType.STRING)
    private ProductType productType;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    private int productPrice;
    private int productStock;

    public void update(PutProductRequest putProductRequest) {
        this.productName = putProductRequest.getProductName();
        this.productType = ProductType.OPTION;
        this.productPrice = putProductRequest.getProductPrice();
        this.productStock = putProductRequest.getProductStock();
        this.productStatus = putProductRequest.getProductStatus();
    }
}
