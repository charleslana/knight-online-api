package com.charles.knightonlineapi.model.dto;

import com.charles.knightonlineapi.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class UserBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private GenderEnum gender;
    private Integer level;
    private Long experience;
    private Long gold;
    private Long silver;
    private Long trophy;
    private Long life;
    private Long armor;
    private Long damage;
    private BigDecimal criticalRate;
    private BigDecimal criticalDamage;
}
