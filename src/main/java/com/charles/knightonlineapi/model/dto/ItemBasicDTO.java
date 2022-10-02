package com.charles.knightonlineapi.model.dto;

import com.charles.knightonlineapi.enums.RarityEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ItemBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String image;
    private Integer level;
    private Long life;
    private Long armor;
    private Long damage;
    private BigDecimal criticalRate;
    private BigDecimal criticalDamage;
    private RarityEnum rarity;
}
