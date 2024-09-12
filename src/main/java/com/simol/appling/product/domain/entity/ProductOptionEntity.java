package com.simol.appling.product.domain.entity;

import com.simol.appling.global.entity.CommonEntity;
import com.simol.appling.product.domain.dto.PostProductOptionDto;
import com.simol.appling.product.domain.dto.PutProductOptionDto;
import com.simol.appling.product.domain.enums.ProductOptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "product_option")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ProductOptionEntity extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOptionId;
    private String productOptionName;
    private int productOptionSort;
    private int productOptionPrice;
    private int productOptionStock;
    @Enumerated(EnumType.STRING)
    private ProductOptionStatus productOptionStatus;
    private String productOptionDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public static ProductOptionEntity from(PostProductOptionDto dto, ProductEntity product) {
        return ProductOptionEntity.builder()
                .productOptionName(dto.getProductOptionName())
                .productOptionSort(dto.getProductOptionSort())
                .productOptionPrice(dto.getProductOptionPrice())
                .productOptionStock(dto.getProductOptionStock())
                .productOptionStatus(dto.getProductOptionStatus())
                .productOptionDescription(dto.getProductOptionDescription())
                .product(product)
                .build();
    }

    public static ProductOptionEntity from(PutProductOptionDto dto, ProductEntity product) {
        ProductOptionEntity entity = ProductOptionEntity.builder()
                .productOptionId(dto.getProductOptionId())
                .productOptionName(dto.getProductOptionName())
                .productOptionSort(dto.getProductOptionSort())
                .productOptionPrice(dto.getProductOptionPrice())
                .productOptionStock(dto.getProductOptionStock())
                .productOptionStatus(dto.getProductOptionStatus())
                .productOptionDescription(dto.getProductOptionDescription())
                .product(product)
                .build();
        return entity;
    }

    public void updateCreateAt(List<ProductOptionEntity> productOptionList) {
        Long targetProductOptionId = this.productOptionId;
        if (targetProductOptionId == null) {
            return ;
        }

        Optional<ProductOptionEntity> findProductOption = productOptionList.stream()
                .filter(f -> f.getProductOptionId().equals(targetProductOptionId))
                .findFirst();

        if (! findProductOption.isPresent()) {
            throw new RuntimeException("해당 상품에 존재하지 않는 옵션 id가 입력되었습니다. proudct_option_id = %d".formatted(targetProductOptionId));
        }

        this.createdAt = findProductOption.get().createdAt;
    }
}
