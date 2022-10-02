package com.charles.knightonlineapi.model.dto;

import com.charles.knightonlineapi.enums.RarityEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ItemDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Min(1)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String image;

    @NotNull
    @Min(1)
    private Integer level;

    @Min(1)
    private Long life;

    @Min(1)
    private Long armor;

    @Min(1)
    private Long damage;

    @DecimalMin(value = "0.01")
    private BigDecimal criticalRate;

    @DecimalMin(value = "0.01")
    private BigDecimal criticalDamage;

    private RarityEnum rarity;
}
