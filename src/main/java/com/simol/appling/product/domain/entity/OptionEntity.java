package com.simol.appling.product.domain.entity;

import com.simol.appling.global.entity.CommonEntity;
import com.simol.appling.product.domain.enums.OptionStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "option")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class OptionEntity extends CommonEntity {
    @Id
    private Long optionId;
    private int optionSort;
    private String optionName;
    private int optionPrice;
    private int optionStock;

    @Enumerated(EnumType.STRING)
    private OptionStatus optionStatus;

    private String optionDescription;
}
