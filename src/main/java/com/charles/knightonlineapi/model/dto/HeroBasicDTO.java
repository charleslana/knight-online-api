package com.charles.knightonlineapi.model.dto;

import com.charles.knightonlineapi.enums.RarityEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class HeroBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String image;
    private Long life;
    private Long armor;
    private Long damage;
    private BigDecimal criticalRate;
    private BigDecimal criticalDamage;
    private BigDecimal block;
    private BigDecimal recoverLife;
    private BigDecimal doubleDamage;
    private BigDecimal reflectedDamage;
    private BigDecimal poison;
    private BigDecimal recoverEnergy;
    private RarityEnum rarity;
}
