package com.simol.appling.product.domain.entity;

import com.simol.appling.global.entity.CommonEntity;
import com.simol.appling.product.domain.dto.PostProductOptionDto;
import com.simol.appling.product.domain.enums.OptionStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_option")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ProductOptionEntity extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;
    private String optionName;
    private int optionSort;
    private int optionPrice;
    private int optionStock;
    @Enumerated(EnumType.STRING)
    private OptionStatus optionStatus;
    private String optionDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public static ProductOptionEntity from(PostProductOptionDto dto, ProductEntity product) {
        return ProductOptionEntity.builder()
                .optionName(dto.getOptionName())
                .optionSort(dto.getOptionSort())
                .optionPrice(dto.getOptionPrice())
                .optionStock(dto.getOptionStock())
                .optionStatus(dto.getOptionStatus())
                .optionDescription(dto.getOptionDescription())
                .product(product)
                .build();
    }
}
