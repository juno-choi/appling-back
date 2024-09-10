package com.simol.appling.product.domain.entity;

import com.simol.appling.global.entity.CommonEntity;
import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.enums.ProductStatus;
import com.simol.appling.product.domain.enums.ProductType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionEntity> productOptionList;

    public static ProductEntity from(PostProductRequest postProductRequest) {
        return ProductEntity.builder()
                .productName(postProductRequest.getProductName())
                .productType(ProductType.OPTION)
                .productStatus(ProductStatus.ON_SALE)
                .productOptionList(new ArrayList<>())
                .build();
    }

    public void update(PutProductRequest putProductRequest) {
        this.productName = putProductRequest.getProductName();
        this.productType = ProductType.OPTION;
        this.productStatus = putProductRequest.getProductStatus();
    }

}
